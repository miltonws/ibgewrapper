package br.com.mws.kafkaline.ibgewrapper.service.cidade;

import br.com.mws.kafkaline.ibgewrapper.gateway.feign.CidadeClient;
import br.com.mws.kafkaline.ibgewrapper.gateway.json.CidadeJson;
import feign.Feign;
import feign.gson.GsonDecoder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultarCidadeService {

    @Cacheable(value = "cidade")
    public List<CidadeJson> execute(String uf) {
        long tempoInicial = System.currentTimeMillis();
        CidadeClient client = Feign.builder()
                .decoder(new GsonDecoder())
                .target(CidadeClient.class, "https://servicodados.ibge.gov.br");

        System.out.printf("Dentro do Servico de cidade: %.3f ms%n", (System.currentTimeMillis() - tempoInicial) / 1000d);
        return client.getCidade(uf);
    }
}
