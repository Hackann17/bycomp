package classesmodelos;

public class Postagem {
    private Usuario usuario;
    private Produto produto;
    private String avaliacaoMercado;
    private String comentario ;
    private Integer likes;


    public Postagem(Usuario usuario, Produto produto, String avaliacaoMercado, String comentario, Integer likes) {
        this.usuario = usuario;
        this.produto = produto;
        this.avaliacaoMercado = avaliacaoMercado;
        this.comentario = comentario;
        this.likes = likes;
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



}
