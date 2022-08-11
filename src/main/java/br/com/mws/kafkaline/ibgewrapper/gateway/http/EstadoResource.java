package br.com.mws.kafkaline.ibgewrapper.gateway.http;


import br.com.mws.kafkaline.ibgewrapper.gateway.json.CidadeJson;
import br.com.mws.kafkaline.ibgewrapper.gateway.json.EstadoJson;
import br.com.mws.kafkaline.ibgewrapper.service.cidade.ConsultarCidadeService;
import br.com.mws.kafkaline.ibgewrapper.service.estado.ConsultarEstadoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estados")
public class EstadoResource {

    @Autowired
    private ConsultarEstadoService consultarEstadoService;

    @Autowired
    private ConsultarCidadeService consultarCidadeService;

    @GetMapping("/")
    public List<EstadoJson> consultarEstados() {
        return consultarEstadoService.execute();
    }

    @GetMapping("/{id}/cidades")
    public List<CidadeJson> consultarCidades(@PathVariable("id") String id) {
        return consultarCidadeService.execute(id);
    }


}
