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
    }

    public List<ItemCarrinho> getItens() {
        return itens;
    }
}