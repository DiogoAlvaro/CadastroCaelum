package br.com.caelum.cadastro;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
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
	public boolean onCreateOptionsMenu(Menu menu){
		//menu.add("Novo");
		//menu.add("Mapa");
		//menu.add("Sincronizar");
		//menu.add("Baixar Provas");
		//menu.add("Preferências");
	
		MenuInflater inflater = this.getMenuInflater();
		inflater.inflate(R.menu.menu_principal, menu);
		
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		
		case R.id.menu_novo:
			Intent intent = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
			startActivity(intent);
			
			break;
		
		case R.id.menu_mapa:
			Toast.makeText(getApplicationContext(), "Mapa", Toast.LENGTH_LONG).show();
			break;
			
		case R.id.menu_enviar_alunos:
			Toast.makeText(getApplicationContext(), "Sincronizar", Toast.LENGTH_LONG).show();
			break;
		
		case R.id.menu_receber_provas:
			Toast.makeText(getApplicationContext(), "Baixar Provas", Toast.LENGTH_LONG).show();
			break;
		
		case R.id.menu_preferencias:
			Toast.makeText(getApplicationContext(), "Preferências", Toast.LENGTH_LONG).show();
			break;
			
		default:
			return super.onOptionsItemSelected(item);
		} ;
		
		
		return super.onOptionsItemSelected(item);
	}
}
