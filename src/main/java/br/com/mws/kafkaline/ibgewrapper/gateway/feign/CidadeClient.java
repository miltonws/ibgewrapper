package br.com.mws.kafkaline.ibgewrapper.gateway.feign;

import br.com.mws.kafkaline.ibgewrapper.gateway.json.CidadeJson;
import feign.Param;
import feign.RequestLine;
import java.util.List;

public interface CidadeClient {

    @RequestLine("GET /api/v1/localidades/estados/{UF}/municipios")
    List<CidadeJson> getCidade(@Param("UF") String uf);
}
