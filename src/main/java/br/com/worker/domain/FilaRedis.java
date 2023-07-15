package br.com.worker.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.Map;

@RedisHash("FilaRedis")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FilaRedis implements Serializable {

    private static final long serialVersionUID = 1L;
    private String chave;
    private Map<String, Object> valor;

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public Map<String, Object> getValor() {
        return valor;
    }

    public void setValor(Map<String, Object> valor) {
        this.valor = valor;
    }
}
