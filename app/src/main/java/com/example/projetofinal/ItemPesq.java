package com.example.projetofinal;

public class ItemPesq {

        private String id ;

        private String nomeM;
        private String enderecoM;
        private  Float precoP;
        private double avaliacaoM;


        public ItemPesq(String id, String nomeM, String enderecoM, double avaliacaoM, String nomeP, Float precoP) {
            this.id = id;
            this.nomeM = nomeM;
            this.enderecoM = enderecoM;
            this.avaliacaoM = avaliacaoM;
            this.nomeP = nomeP;
            this.precoP = precoP;
        }
        private String nomeP;

        public String getId() {
            return id;
        }

        public String getNomeM() {
            return nomeM;
        }

        public String getEnderecoM() {
            return enderecoM;
        }

        public double getAvaliacaoM() {
            return avaliacaoM;
        }

        public String getNomeP() {
            return nomeP;
        }

        public Float getPrecoP() {
            return precoP;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setNomeM(String nomeM) {
            this.nomeM = nomeM;
        }

        public void setEnderecoM(String enderecoM) {
            this.enderecoM = enderecoM;
        }

        public void setPrecoP(Float precoP) {
            this.precoP = precoP;
        }

        public void setAvaliacaoM(double avaliacaoM) {
            this.avaliacaoM = avaliacaoM;
        }

        public void setNomeP(String nomeP) {
            this.nomeP = nomeP;
        }
    }

