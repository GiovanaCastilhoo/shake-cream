package com.shakecream.app.state;

import com.shakecream.app.models.ItemCarrinho;
import java.util.ArrayList;
import java.util.List;

public class CarrinhoGlobal {
    private static CarrinhoGlobal instancia;
    private final List<ItemCarrinho> itens;

    private CarrinhoGlobal() {
        this.itens = new ArrayList<>();
    }

    public static CarrinhoGlobal getInstancia() {
        if (instancia == null) {
            instancia = new CarrinhoGlobal();
        }
        return instancia;
    }

    public void adicionarItem(ItemCarrinho item) {
        itens.add(item);
        System.out.println("--- ITEM SALVO COM SUCESSO NO CARRINHO ---");
        System.out.println("Produto: " + item.getProdutoNome() + " (" + item.getTamanho() + ")");
        System.out.println("Quantidade: " + item.getQuantidade());
        System.out.println("Total do Item: R$ " + String.format("%.2f", item.getValorTotal()));
        System.out.println("Total de itens no carrinho atualmente: " + itens.size());
    }

    public List<ItemCarrinho> getItens() {
        return itens;
    }
}