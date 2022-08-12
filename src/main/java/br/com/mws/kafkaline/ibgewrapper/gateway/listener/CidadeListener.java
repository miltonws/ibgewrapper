package br.com.mws.kafkaline.ibgewrapper.gateway.listener;

import br.com.mws.kafkaline.ibgewrapper.gateway.json.CidadeJson;
import br.com.mws.kafkaline.ibgewrapper.gateway.json.CidadeList;
import br.com.mws.kafkaline.ibgewrapper.gateway.json.EstadoRequestTopicJson;
import br.com.mws.kafkaline.ibgewrapper.service.cidade.ConsultarCidadeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CidadeListener {

    @Autowired
    private ConsultarCidadeService consultarCidadeService;

    @KafkaListener(topics = "${kafka.topic.request-topic-cidade}")
    public Message<String> execute(String in, @Header(KafkaHeaders.REPLY_TOPIC) byte[] replyTo,
                                   @Header(KafkaHeaders.CORRELATION_ID) byte[] correlation) throws JsonProcessingException {

        long tempoInicial = System.currentTimeMillis();

        ObjectMapper mapper = new ObjectMapper();
        EstadoRequestTopicJson json = mapper.readValue(in, EstadoRequestTopicJson.class);

        List<CidadeJson> listCidades = consultarCidadeService.execute(json.getUf());
        String jsonReturn = mapper.writeValueAsString(CidadeList.builder().list(listCidades).build());

        System.out.printf("Dentro do Listener de cidade: %.3f ms%n", (System.currentTimeMillis() - tempoInicial) / 1000d);

        return MessageBuilder.withPayload(jsonReturn)
                .setHeader(KafkaHeaders.TOPIC, replyTo)
                .setHeader(KafkaHeaders.CORRELATION_ID, correlation)
                .build();
    }
}
