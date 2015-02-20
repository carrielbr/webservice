package webservice

class Search {

    String origem
    String destino
    String rota
    String mapa
    int distancia
    
    static constraints = {
        origem(blank:false,nullable:false)
        destino(blank:false,nullable:false)
        rota(blank:false,nullable:false)
        mapa(blank:false,nullable:false)
        distancia(blank:false,nullable:false)
    }
    
    static mapping = {
        sort distancia: 'asc'
        table "cad_search"
    }
    
}
