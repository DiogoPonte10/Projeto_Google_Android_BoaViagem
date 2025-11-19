package br.com.casadocodigo.boaviagem;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import static br.com.casadocodigo.boaviagem.Constantes.*;

import com.google.api.client.googleapis.extensions.android2.auth.GoogleAccountManager;

import java.io.IOException;

public class BoaViagemActivity extends Activity {
    private EditText usuario;
    private EditText senha;
    private CheckBox manterConectado;
    private SharedPreferences preferencias;
    private GoogleAccountManager accountManager;
    private Account conta;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        accountManager = new GoogleAccountManager(this);

        usuario = (EditText) findViewById(R.id.usuario);
        senha = (EditText) findViewById(R.id.senha);
        manterConectado = (CheckBox) findViewById(R.id.manterConectado);

        preferencias = getSharedPreferences(PREFERENCIAS, MODE_PRIVATE);
        boolean conectado = preferencias.getBoolean(MANTER_CONECTADO, false);

        if (conectado) {
            solicitarAutorizacao();
        }
    }

    private void iniciarDashBoard() {
        startActivity(new Intent(this, DashboardActivity.class));
    }

    public void entrarOnClick(View v) {
        String usuarioInformado = usuario.getText().toString();
        String senhaInformada = senha.getText().toString();

        autenticar(usuarioInformado, senhaInformada);

//        if ("leitor".equals(usuarioInformado) &&
//            "123".equals(senhaInformada)) {
//
//            SharedPreferences preferencias = getPreferences(MODE_PRIVATE);
//
//            SharedPreferences.Editor editor = preferencias.edit();
//            editor.putBoolean(MANTER_CONECTADO, manterConectado.isChecked());
//            editor.commit();
//
//            startActivity(new Intent(this, DashboardActivity.class));
//        } else {
//            String mensagemErr = getString(R.string.erro_autenticacao);
//            Toast toast = Toast.makeText(this, mensagemErr, Toast.LENGTH_SHORT);
//            toast.show();
//        }
    }

    private void autenticar(final String nomeConta, String senha) {
        conta = accountManager.getAccountByName(nomeConta);

        if (conta == null){
            Toast.makeText(this, R.string.conta_inexistente, Toast.LENGTH_SHORT).show();
            return;
        }

        Bundle bundle = new Bundle();
        bundle.putString(AccountManager.KEY_ACCOUNT_NAME, nomeConta);
        bundle.putString(AccountManager.KEY_PASSWORD, senha);

        accountManager.getAccountManager().confirmCredentials(conta, bundle, this, new AutenticacaoCallback(), null);
    }

    private void solicitarAutorizacao() {
        String tokenAcesso = preferencias.getString(TOKEN_ACESSO, null);
        String nomeConta = preferencias.getString(NOME_CONTA, null);

        if (tokenAcesso != null) {
            accountManager.invalidateAuthToken(tokenAcesso);
            conta = accountManager.getAccountByName(nomeConta);
        }

        accountManager.getAccountManager().getAuthToken(conta,
                                                        AUTH_TOKEN_TYPE,
                                                        null,
                                                        this,
                                                        new AutorizacaoCallback(),
                                                        null);
    }

    private class AutenticacaoCallback implements AccountManagerCallback<Bundle> {
        @Override
        public void run(AccountManagerFuture<Bundle> future) {
            try {
                Bundle bundle = future.getResult();
                if (bundle.getBoolean(AccountManager.KEY_BOOLEAN_RESULT)) {
                    solicitarAutorizacao();
                } else {
                    Toast.makeText(getBaseContext(), getString(R.string.erro_autenticacao), Toast.LENGTH_LONG).show();
                }

            } catch (OperationCanceledException e) {

            } catch (AuthenticatorException e) {

            } catch (IOException e) {

            }
        }
    }

    private class AutorizacaoCallback implements AccountManagerCallback<Bundle> {
        @Override
        public void run(AccountManagerFuture<Bundle> future) {
            try {
                Bundle bundle = future.getResult();
                String nomeConta = bundle.getString(AccountManager.KEY_ACCOUNT_NAME);
                String tokenAcesso = bundle.getString(AccountManager.KEY_AUTHTOKEN);

                gravarTokenAcesso(nomeConta, tokenAcesso);

                iniciarDashBoard();

                if (bundle.getBoolean(AccountManager.KEY_BOOLEAN_RESULT)) {
                    iniciarDashBoard();
                } else {
                    Toast.makeText(getBaseContext(), getString(R.string.erro_autenticacao), Toast.LENGTH_LONG).show();
                }

            } catch (OperationCanceledException e) {

            } catch (AuthenticatorException e) {

            } catch (IOException e) {

            }
        }
    }

    private void gravarTokenAcesso(String nomeConta, String tokenAcesso) {
        SharedPreferences.Editor editor = preferencias.edit();
        editor.putString(NOME_CONTA, nomeConta);
        editor.putString(TOKEN_ACESSO, tokenAcesso);
        editor.commit();
    }
}
