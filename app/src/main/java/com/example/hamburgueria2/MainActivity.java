package com.example.hamburgueria2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textResultado;
    private TextView textValorFinal;
    private TextView textNome;
    private EditText editContador;
    double valorAdicional = 2;
    double valorAdicionalOnion = 3;
    double valorHamburgue = 20;
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editContador = (EditText) findViewById(R.id.editContador);
        textNome = (EditText) findViewById(R.id.editTextNome);
        textResultado = (TextView) findViewById(R.id.textViewResultado);
        textValorFinal = (TextView) findViewById(R.id.textValorFinal);
    }

    public void incrementar(View view) {

        quantity = quantity + 1;
        editContador.setText("" + quantity);

    }

    public void decrementar(View view) {
        quantity = quantity - 1;
        editContador.setText("" + quantity);
    }

    public void pedido(View view) {
        String nomeCliente = textNome.getText().toString();

        if (nomeCliente.trim().equals("")) {
            mensagemErro("Erro!", "Por favor informe o nome do cliente");
            return;
        }

        if (quantity <= 0) {
            mensagemErro("Erro!", "Selecione a quantidade desejada!");
            return;
        }

        boolean isCheckedBacon = ((CheckBox) findViewById(R.id.checkBacon)).isChecked();
        boolean isCheckedQueijo = ((CheckBox) findViewById(R.id.checkQueijo)).isChecked();
        boolean isCheckedOnion = ((CheckBox) findViewById(R.id.checkOnion)).isChecked();

        double valorTotal = valorHamburgue * quantity;
        valorTotal += isCheckedBacon ? valorAdicional * quantity : 0;
        valorTotal += isCheckedQueijo ? valorAdicional * quantity : 0;
        valorTotal += isCheckedOnion ? valorAdicionalOnion * quantity : 0;

        String mensagem = "Nome Cliente: " + nomeCliente + "\n";
        mensagem += "Tem Bacon? "+ (isCheckedBacon ? "Sim" : "Não") + "\n";
        mensagem += "Tem Queijo? "+ (isCheckedQueijo ? "Sim" : "Não") + "\n";
        mensagem += "Tem Onion Rings? "+ (isCheckedOnion ? "Sim" : "Não") + "\n";
        mensagem += "Quantidade: "+ (quantity) + "\n";

        textResultado.setText(mensagem);

        textValorFinal.setText("Preço final: R$: "+ valorTotal);

        composeEmail( "Pedido de ("+nomeCliente+")", mensagem);

    }

    public void mensagemErro(String title, String message) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.show();
    }

    public void composeEmail(String assunto, String mensagem) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_TEXT, mensagem );
        intent.putExtra(Intent.EXTRA_SUBJECT, assunto);
        startActivity(Intent.createChooser(intent, assunto));
    }

}