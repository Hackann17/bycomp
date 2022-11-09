package classesmodelos;

public class Postagem {
    private Usuario usuario;
    private Produto produto;
    private Mercado avaliacaoMecrado;
    private String comentario ;
    private Integer likes;

    public Postagem(Usuario usuario, Produto produto, String comentario, Integer likes) {
        this.usuario = usuario;
        this.produto = produto;
        this.comentario = comentario;
        this.likes = likes;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Produto getProduto() {
        return produto;
    }

    public String getComentario() {
        return comentario;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }
}
