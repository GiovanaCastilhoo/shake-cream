package com.shakecream.app.ui;
import com.shakecream.app.models.Additional;

public class AdditionalItem {
    private Additional additional;
    private boolean selecionado;

    public AdditionalItem(Additional additional) {
        this.additional = additional;
        this.selecionado = false;
    }

    public Additional getAdditional() { return additional; }
    public boolean isSelecionado() { return selecionado; }
    public void setSelecionado(boolean selecionado) { this.selecionado = selecionado; }
}