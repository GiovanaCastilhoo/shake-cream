package com.shakecream.app.services;

import com.shakecream.app.models.ItemCarrinho;
import java.util.List;

public class PedidoService {

    // Método que simula o salvamento no banco de dados Back-end
    public boolean salvarPedido(List<ItemCarrinho> itens, String formaPagamento, double total) {
        if (itens == null || itens.isEmpty()) {
            System.err.println("[Erro no Back-end] Tentativa de salvar um pedido sem itens.");
            return false;
        }

        // Aqui simula a lógica que a Giovana integraria com o Banco de Dados (MySQL)
        System.out.println("=========================================");
        System.out.println("💾 [BACK-END] ENVIANDO PEDIDO AO BANCO...");
        System.out.println("Forma de Pagamento: " + formaPagamento.toUpperCase());
        System.out.println("Quantidade de itens: " + itens.size());
        System.out.println("Valor Total Gravado: R$ " + String.format("%.2f", total));
        System.out.println("✅ PEDIDO GRAVADO COM SUCESSO NO BANCO DE DADOS!");
        System.out.println("=========================================");
        
        return true;
    }
}