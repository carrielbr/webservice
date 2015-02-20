package webservice

class MalhaLogistica {

    String origem
    String destino
    int distancia
    
    static constraints = {
        origem(blank:false,nullable:false)
        destino(blank:false,nullable:false)
        distancia(blank:false,nullable:false)
    }
    
    static mapping = {
        sort distancia: 'asc'
        table "cad_malha_logistica"
    }
}
