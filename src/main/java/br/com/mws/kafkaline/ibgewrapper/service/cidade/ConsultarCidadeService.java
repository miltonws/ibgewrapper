package br.com.mws.kafkaline.ibgewrapper.service.cidade;

import br.com.mws.kafkaline.ibgewrapper.gateway.feign.CidadeClient;
import br.com.mws.kafkaline.ibgewrapper.gateway.json.CidadeJson;
import feign.Feign;
import feign.gson.GsonDecoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultarCidadeService {

    public List<CidadeJson> execute(String uf) {
        CidadeClient client = Feign.builder()
                .decoder(new GsonDecoder())
                .target(CidadeClient.class, "https://servicodados.ibge.gov.br");

        return client.getCidade(uf);
    }
}
