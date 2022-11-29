package classesmodelos;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Mercado implements Serializable {
    private String nome;
    private String cnpj;
    private String bairro;
    private String rua;
    private String cidade;
    private double avaliacao;


    public Mercado(String nome, String cnpj, String cidade, String bairro, String rua,double avaliacao) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.avaliacao = avaliacao;
    }

    public String getNome() {
        return nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getCidade() { return cidade; }

    public String getBairro() {
        return bairro;
    }

    public String getRua() {
        return rua;
    }

    public double getAvaliacao() {
        return avaliacao;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpnj(String cnpj) {
        this.nome = nome;
    }

    public void setBairro(String bairro) {
        this.nome = nome;
    }

    public void setRua(String rua) {
        this.nome = nome;
    }

    public void setAvaliacao(double avaliacao) {
        this.avaliacao = avaliacao;
    }

    //configura a avaliação de cada mercado de acordo com as avaliações na lista avaliacoes
    public static void configurarAvaliacoesDosMercados(List<Mercado> mercados, List<Avaliacao> avaliacoes){
        for (Mercado m : mercados) {
            String mCnpj = m.getCnpj();
            if(m.getAvaliacao()==0)
                for (Avaliacao av : avaliacoes) {
                    String avCnpj = av.getCnpj();
                    if (av.getCnpj().equals(m.getCnpj())) {
                        double avaliacao = av.getAvaliacao();
                        m.setAvaliacao(avaliacao);
                        break;
                    }
                }
        }
    }

}
