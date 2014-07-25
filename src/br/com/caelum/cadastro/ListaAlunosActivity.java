package br.com.caelum.cadastro;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ListaAlunosActivity extends ActionBarActivity {

	private ListView listaAlunos;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listagem_alunos);
		
		String[] alunos = {"Anderson", "Felipe", "Guilherme"};
		
		this.listaAlunos = (ListView) findViewById(R.id.lista_alunos);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunos);
		
		listaAlunos.setAdapter(adapter);
		
		
		listaAlunos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adpter, View view,
					int position, long id) {
				
				Toast.makeText(ListaAlunosActivity.this, "Posição Selecionada: "+ position, Toast.LENGTH_LONG).show();
				
			}
		});
		
		
		listaAlunos.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,	
					int position, long id) {
				
					Toast.makeText(ListaAlunosActivity.this, "Nome do Aluno Selecionado: "+ adapter.getItemAtPosition(position), Toast.LENGTH_LONG).show();
				
				return false;
			}
		});
		
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
