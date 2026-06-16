package com.shakecream.app.models;

import java.util.List;

public class ItemCarrinho {
    private String produtoNome;
    private String tamanho;
    private List<Additional> adicionaisEscolhidos;
    private String observacao; // ✨ Adicionado o campo de texto para a observação
    private int quantidade;
    private double valorTotal;
    private String pathImagem;

    // Construtor completo atualizado para receber a observação da tela de detalhes
    public ItemCarrinho(String produtoNome, String tamanho, List<Additional> adicionaisEscolhidos,
                        String observacao, int quantidade, double valorTotal, String pathImagem) {
        this.produtoNome = produtoNome;
        this.tamanho = tamanho;
        this.adicionaisEscolhidos = adicionaisEscolhidos;
        this.observacao = observacao; // ✨ Salvando a observação
        this.quantidade = quantidade;
        this.valorTotal = valorTotal;
        this.pathImagem = pathImagem;
    }

    // --- GETTERS E SETTERS ---

    public String getProdutoNome() {
        return produtoNome;
    }

    public String getTamanho() {
        return tamanho;
    }

    public List<Additional> getAdicionaisEscolhidos() {
        return adicionaisEscolhidos;
    }

    // ✨ O método que o Java estava reclamando que não existia:
    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public String getPathImagem() {
        return pathImagem;
    }
}