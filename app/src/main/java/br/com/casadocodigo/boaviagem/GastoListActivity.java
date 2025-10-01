package br.com.casadocodigo.boaviagem;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GastoListActivity extends ListActivity implements AdapterView.OnItemClickListener {

    private List<Map<String, Object>> gastos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[] de = {"data", "descricao", "valor", "categoria"};
        int[] para = {R.id.data, R.id.destino, R.id.valor, R.id.categoria};

        SimpleAdapter adapter = new SimpleAdapter(this, listarGastos(),
                R.layout.lista_gasto, de ,para);

        adapter.setViewBinder(new GastoViewBinder());

        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
//        setListAdapter(new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, listarGastos()));
//        ListView listView = getListView();
//        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String, Object> map = gastos.get(position);
        String descricao = (String) map.get("descricao");
        String mensagem = "Gasto selecionado: " + descricao;
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show();
//        TextView textView = (TextView) view;
//        Toast.makeText(this, "Gasto selecionado: " + textView.getText(), Toast.LENGTH_SHORT).show();
    }

    private List<Map<String, Object>> listarGastos() {
        gastos = new ArrayList<Map<String, Object>>();

        Map<String, Object> item = new HashMap<String, Object>();
        item.put("data", "04/02/2022");
        item.put("descricao", "Diária Hotel");
        item.put("valor", "R$ 260,00");
        item.put("categoria", R.color.categoria_hospedagem);
        gastos.add(item);

        item = new HashMap<String, Object>();
        item.put("data", "04/02/2022");
        item.put("descricao", "Sanduíche");
        item.put("valor", "R$ 19,90");
        item.put("categoria", R.color.categoria_alimentacao);
        gastos.add(item);

        item = new HashMap<String, Object>();
        item.put("data", "15/05/2022");
        item.put("descricao", "Táxi Aeroporto - Hotel");
        item.put("valor", "R$ 34,00");
        item.put("categoria", R.color.categoria_transporte);
        gastos.add(item);

        return gastos;
    }
//    private List<String> listarGastos() {
//        return Arrays.asList("Sanduíche R$ 19,90", "Táxi Aeroporto - Hotal R$ 34,00", "Revista R$ 12,00");
//    }

    private class GastoViewBinder implements SimpleAdapter.ViewBinder {

        private String dataAnterior = "";

        @Override
        public boolean setViewValue(View view, Object data, String textRepresentation) {
            if (view.getId() == R.id.data) {
                if (!dataAnterior.equals(data)) {
                    TextView textView = (TextView) view;
                    textView.setText(textRepresentation);
                    dataAnterior = textRepresentation;
                    view.setVisibility(View.VISIBLE);
                } else {
                    view.setVisibility(View.GONE);
                }
                return true;
            }
            if (view.getId() == R.id.categoria) {
                Integer id = (Integer) data;
                view.setBackgroundColor(getResources().getColor(id));
                return true;
            }
            return false;
        }
    }
}
