package br.com.mws.kafkaline.ibgewrapper.service.estado;

import br.com.mws.kafkaline.ibgewrapper.gateway.feign.EstadoClient;
import br.com.mws.kafkaline.ibgewrapper.gateway.json.EstadoJson;
import feign.Feign;
import feign.gson.GsonDecoder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultarEstadoService {

    @Cacheable(value = "estado")
    public List<EstadoJson> execute() {
        long tempoInicial = System.currentTimeMillis();
        EstadoClient client = Feign.builder()
                .decoder(new GsonDecoder())
                .target(EstadoClient.class, "https://servicodados.ibge.gov.br");

        System.out.printf("Dentro do Servico de estado: %.3f ms%n", (System.currentTimeMillis() - tempoInicial) / 1000d);
        return client.getEstado();
    }
}
