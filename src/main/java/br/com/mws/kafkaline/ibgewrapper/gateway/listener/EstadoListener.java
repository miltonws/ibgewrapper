package br.com.mws.kafkaline.ibgewrapper.gateway.listener;

import br.com.mws.kafkaline.ibgewrapper.gateway.json.EstadoJson;
import br.com.mws.kafkaline.ibgewrapper.gateway.json.EstadoList;
import br.com.mws.kafkaline.ibgewrapper.service.estado.ConsultarEstadoService;
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
public class EstadoListener {

    @Autowired
    private ConsultarEstadoService consultarEstadoService;

    /*
        Classe fica ouvindo o topico de request, quando chega mensagem executa servico e envia no sendTo.
        No kafka.topic.request-topic, esta configurado no headers o reply origem que ele deve devolver(KafkaHeaders.REPLY_TOPIC)
        Ver classe do servico ibgeservice.ConsultarEstadoService:
            producerRecord.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, requestReplyTopic.getBytes()));
    */
    @KafkaListener(topics = "${kafka.topic.request-topic}")
    public Message<String> execute(@Header(KafkaHeaders.REPLY_TOPIC) byte[] replyTo,
                                   @Header(KafkaHeaders.CORRELATION_ID) byte[] correlation) throws JsonProcessingException {

        long tempoInicial = System.currentTimeMillis();

        ObjectMapper mapper = new ObjectMapper();
        List<EstadoJson> listEstados = consultarEstadoService.execute();
        String jsonReturn = mapper.writeValueAsString(EstadoList.builder().list(listEstados).build());

        System.out.printf("Dentro do Listener de estado: %.3f ms%n", (System.currentTimeMillis() - tempoInicial) / 1000d);

        return MessageBuilder.withPayload(jsonReturn)
                .setHeader(KafkaHeaders.TOPIC, replyTo)
                .setHeader(KafkaHeaders.CORRELATION_ID, correlation)
                .build();
    }
}
