package classesmodelos;

public class Usuario {
    private  String localusuario;

    private  String nome;
    private  String email;
    private  String senha;

    public Usuario(String localusuario, String nome, String email, String senha) {
        this.localusuario = localusuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String nome, String email, String senha) {
        this.localusuario = localusuario;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Usuario(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public Usuario() {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }



    public String getLocalusuario() {
        return localusuario;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public void setLocalusuario(String localusuario) {
        this.localusuario = localusuario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
