import webservice.MalhaLogistica

class BootStrap {

    def init = { servletContext ->
        
        //adiciona os dados na tabela MalhaLogistica do banco de dados caso os mesmos nao exista
        def ab = MalhaLogistica.findByOrigemAndDestino('A','B') ?:
        new MalhaLogistica(origem: "A", destino: "B", distancia: 10).save(failOnError: true)
        
        def bd = MalhaLogistica.findByOrigemAndDestino('B','D') ?:
        new MalhaLogistica(origem: "B", destino: "D", distancia: 15).save(failOnError: true)
        
        def ac = MalhaLogistica.findByOrigemAndDestino('A','C') ?:
        new MalhaLogistica(origem: "A", destino: "C", distancia: 20).save(failOnError: true)
        
        def cd = MalhaLogistica.findByOrigemAndDestino('C','D') ?:
        new MalhaLogistica(origem: "C", destino: "D", distancia: 30).save(failOnError: true)
        
        def be = MalhaLogistica.findByOrigemAndDestino('B','E') ?:
        new MalhaLogistica(origem: "B", destino: "E", distancia: 50).save(failOnError: true)
        
        def de = MalhaLogistica.findByOrigemAndDestino('D','E') ?:
        new MalhaLogistica(origem: "D", destino: "E", distancia: 30).save(failOnError: true)
        
    }
    def destroy = {
    }
}
