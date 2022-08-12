package br.com.mws.kafkaline.ibgewrapper.service.estado;

import br.com.mws.kafkaline.ibgewrapper.gateway.feign.EstadoClient;
import br.com.mws.kafkaline.ibgewrapper.gateway.json.EstadoJson;
import feign.Feign;
import feign.gson.GsonDecoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultarEstadoService {

    public List<EstadoJson> execute() {
        EstadoClient client = Feign.builder()
                .decoder(new GsonDecoder())
                .target(EstadoClient.class, "https://servicodados.ibge.gov.br");

        return client.getEstado();
    }
}
