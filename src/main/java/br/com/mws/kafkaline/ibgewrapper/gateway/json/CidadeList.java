package br.com.mws.kafkaline.ibgewrapper.gateway.json;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CidadeList {
    private List<CidadeJson> list;
}
