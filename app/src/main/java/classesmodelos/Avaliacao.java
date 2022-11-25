package classesmodelos;

import java.util.ArrayList;
import java.util.List;

public class Avaliacao {
    private double avaliacao;
    private String cnpj;

    public Avaliacao(double avaliacao, String cnpj){
        this.avaliacao = avaliacao;
        this.cnpj = cnpj;
    }

    public double getAvaliacao() { return avaliacao; }
    public String getCnpj() { return cnpj; }


    //separar lista de avaliações por mercado
    public static List<Avaliacao> avaliacoesMercado(List<Avaliacao> avaliacoes){
        List<Avaliacao> avaliacoesMercado = new ArrayList<>();
        List<String> cnpjs = new ArrayList<>();
        //adicionar os cnpjs de avaliacoes na lista de cnpjs de forma única
        for (int i = 0; i < avaliacoes.size(); i++){
            if(!cnpjs.contains(avaliacoes.get(i).getCnpj()))
                cnpjs.add(avaliacoes.get(i).getCnpj());
        }

        //adicionar valores na lista de avaliações
        for(String cnpj : cnpjs){
            List<Double> avaliacoesCnpj = new ArrayList<>();
            for(int i = 0; i < avaliacoes.size(); i++){
                if(avaliacoes.get(i).getCnpj().equals(cnpj)){
                    avaliacoesCnpj.add(avaliacoes.get(i).getAvaliacao());
                }
            }
            double avaliacao=0;
            for (Double av : avaliacoesCnpj) {
                avaliacao += av;
            }
            //adicionar avaliação na lista com a média das avaliações
            avaliacoesMercado.add(new Avaliacao(avaliacao/avaliacoesCnpj.size(),cnpj));
        }

        return avaliacoesMercado;
    }
}
