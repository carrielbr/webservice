package webservice

/**
 * Controller de Logistica
 *
 * @author Caio Carriel
 * @version 1.0 - 20/02/2015
 * 
 */

import grails.converters.*
class LogisticaController {

    def index() { 
        
    }
    
    def searchLogist(){
        println params
        
        def response = [:]
        //pega os valores passados por parametro
        
        //verifica se os dados sao validos
        if(!params.origem || !params.destino || !params.mapa || !params.valor || !params.autonomia){
            response.error = "Preencher todos os dados"
            withFormat{
                html response
                json { render response as JSON }
                xml { render response as XML }
            }
            return
        }
        
        //cria as variaveis, retira espaco e deixa tudo em maiusculo
        def orig = ((params.origem).toUpperCase()).replaceAll(" ","")
        def dest = ((params.destino).toUpperCase()).replaceAll(" ","")
        def map = ((params.mapa).toUpperCase()).replaceAll(" ","")
        def val = (params.valor).replace(",",".")
        def aut = params.autonomia
        
        //verifica se os parametros de origem sao validos
        if(!orig && (orig != "A") || (orig != "B") || (orig != "C") || (orig != "D") || (orig != "E") ){
            response.error = "Origem invalida!"
            withFormat{
                html response
                json { render response as JSON }
                xml { render response as XML }
            }
            return
        }
        
        //verifica se os parametros de destino sao validos
        
        if(!dest && (dest != "A") || (dest != "B") || (dest != "C") || (dest != "D") || (dest != "E") ){
            response.error = "Destino invalido!"
            withFormat{
                html response
                json { render response as JSON }
                xml { render response as XML }
            }
            return
        }
        
        //verifica se ja foi feita a consulta anteriormente
        def consultado = Search.findByMapaAndOrigemAndDestino(map, orig, dest)
        if(consultado){
            response.rota = consultado?.rota
            response.custo = ((new Integer(consultado?.distancia) / new Integer(aut)) * Float.parseFloat(val))
        }else{
            //caso nao exita entao e faz os calculos do caminho mais proximo

            def checkDir = MalhaLogistica.findByOrigemAndDestino(orig, dest) //verifica se tem algum caminho direto
            if(checkDir){
                response.rota = checkDir?.origem + checkDir?.destino 
                response.custo = ((new Integer(checkDir?.distancia) / new Integer(aut)) * Float.parseFloat(val))
                
                //adciona a pesquisa na tabela search
                def searchInstance = new Search()
                    searchInstance.rota = orig + "" + dest
                    searchInstance.origem = orig
                    searchInstance.distancia = new Integer(checkDir?.distancia)
                    searchInstance.destino = dest
                    searchInstance.mapa = map
                    searchInstance.save(flush:true)
            }else{
                def alterDir = MalhaLogistica.findByOrigemAndDestino(dest, orig) //verifica se tem algum caminho direto inverso
                if(alterDir){
                    response.rota = alterDir?.origem + alterDir?.destino 
                    response.custo = ((new Integer(alterDir?.distancia) / new Integer(aut)) * Float.parseFloat(val))
                    
                    //adciona a pesquisa na tabela search
                    def searchInstance = new Search()
                        searchInstance.rota = orig + "" + dest
                        searchInstance.origem = orig
                        searchInstance.distancia = new Integer(alterDir?.distancia)
                        searchInstance.destino = dest
                        searchInstance.mapa = map
                        searchInstance.save(flush:true)
                    
                }else{
                    def ret = analiseCaminho(orig, dest).split(",")

                    response.rota = ret[0]
                    response.custo = ((new Integer(ret[1]) / new Integer(aut)) * Float.parseFloat(val))
                    
                    //adciona a pesquisa na tabela search
                    def searchInstance = new Search()
                        searchInstance.rota = orig + "" + dest
                        searchInstance.origem = orig
                        searchInstance.distancia = new Integer(ret[1])
                        searchInstance.destino = dest
                        searchInstance.mapa = map
                        searchInstance.save(flush:true)
                }
            }
        }
       
        
        //retorna os valores no formato desejado
        withFormat{
            html response
            json { render response as JSON }
            xml { render response as XML }
        }
    }
    
    //metodo utilizado para calcular o melhor caminho
    def analiseCaminho(init, fim){
         def alf = "ABCDE"
         def initAlf = alf.indexOf(init)
         def endAlf = alf.indexOf(fim)
         
        def subAlf
        
        def invert = false
        
        if(initAlf > endAlf){
            subAlf = alf.substring(endAlf, initAlf + 1)
            invert = true
        }else{
            subAlf = alf.substring(initAlf, endAlf + 1)
        }
                            
            def lstAlf = []
            //converte a string em um array
            for(int x = 0; x < (subAlf.length()); x++){
                lstAlf << subAlf.substring(x, x+1)
            }
            
            def rota = ""
            def dist = 0
            
            def ret = 0
            def val = 0
            while(ret == 0){                
                def result = MalhaLogistica.findByOrigemAndDestino(lstAlf[val], lstAlf[(val + 1)])
                if(result){
                    if(rota == ""){
                        rota = rota + "" + result?.origem + "" + result?.destino
                    }else{
                        rota = rota + "" + result?.destino
                    }
                    dist = dist + new Integer(result?.distancia)
                }                
                result = MalhaLogistica.findByOrigemAndDestino(lstAlf[(val + 1)], lstAlf[(new Integer(lstAlf.size()) - 1)])
                if(result){
                    rota = rota + "" + result?.destino
                    dist = dist + new Integer(result?.distancia)
                    ret = 1
                }
                val++                
            }
        if(invert == true){
            rota = rota.reverse()
        }
         
        return rota + "," + dist
                        
     }
}