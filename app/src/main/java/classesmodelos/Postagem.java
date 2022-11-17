package classesmodelos;

import java.io.Serializable;
import java.util.List;

public class Postagem implements Serializable {
    private Usuario usuario;
    private Produto produto;
    private String avaliacaoMercado;
    private String comentario;
    private Integer likes;

    public void setAdicional(List<String> adicional) {
        this.adicional = adicional;
    }

    private List<String> adicional;

    public Postagem(Usuario usuario, Produto produto, String avaliacaoMercado, String comentario, Integer likes, List<String> adicional) {
        this.usuario = usuario;
        this.produto = produto;
        this.avaliacaoMercado = avaliacaoMercado;
        this.comentario = comentario;
        this.likes = likes;
        this.adicional = adicional;
    }

    public Postagem(String avaliacaoMercado, String comentario, List<String> adicional) {
        this.avaliacaoMercado = avaliacaoMercado;
        this.comentario = comentario;
        this.adicional = adicional;
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public String getAvaliacaoMercado() {
        return avaliacaoMercado;
    }

    public void setAvaliacaoMercado(String avaliacaoMercado) {
        this.avaliacaoMercado = avaliacaoMercado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public List<String> getAdicional() {
        return adicional;
    }



}
