package br.com.caelum.cadastro;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
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
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.modelo.Aluno;
import br.com.caelum.modelo.Extras;

public class ListaAlunosActivity extends Activity {

	private ListView listaAlunos;
	private Aluno alunoSelecionado;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.listagem_alunos);
		
		this.listaAlunos = (ListView) findViewById(R.id.lista_alunos);
		
		carregaLista();

		
		listaAlunos.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				//Toast.makeText(ListaAlunosActivity.this, "Posição Selecionada: "+ position, Toast.LENGTH_LONG).show();
				
				Intent edicao = new Intent(ListaAlunosActivity.this, FormularioActivity.class);
				edicao.putExtra(Extras.ALUNO_SELECIONADO, (Aluno) adapter.getItemAtPosition(position));//Enviando o objeto 'Aluno' pela Intent. Nesse caso, é obrigatório que a classe 'Aluno' seja serializável.
				startActivity(edicao);
			}
		});
		
		
		listaAlunos.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
				
				//Guarda aluno escolhido em atributo
				alunoSelecionado = (Aluno) adapter.getItemAtPosition(position);
				return false;
			}
		});
		
		registerForContextMenu(listaAlunos);
	}
	

	@Override
	protected void onResume() {
		super.onResume();
		this.carregaLista();
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
	
	/* Menus de Contexto - Clique Longo sobre itens da Lista */
	@Override
	public void onCreateContextMenu(ContextMenu menu, View view, ContextMenuInfo menuInfo){
		
		super.onCreateContextMenu(menu, view, menuInfo);
		
		MenuInflater inflater = this.getMenuInflater();
		inflater.inflate(R.menu.menu_contexto, menu);
				
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item){
		
		switch (item.getItemId()) {
		case R.id.menu_contexto_acharNoMapa:
			Intent intentMapa = new Intent(Intent.ACTION_VIEW);
			intentMapa.setData(Uri.parse("geo:0,0?z=14&q="+ alunoSelecionado.getEndereco()));
			item.setIntent(intentMapa);
			break;
		
		case R.id.menu_contexto_deletar:
			AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
			dao.exclui(alunoSelecionado);
			dao.close();
			Toast.makeText(ListaAlunosActivity.this, "Aluno(a) "+ alunoSelecionado.getNome() +" foi excluido(a).", Toast.LENGTH_LONG).show();
			carregaLista();
			break;
		
		case R.id.menu_contexto_enviarEmail:
			Intent intentEmail = new Intent(Intent.ACTION_SENDTO);
			intentEmail.setType("*/*");
			intentEmail.putExtra(Intent.EXTRA_EMAIL, "diogo@dalvarodesign.com.br");
			intentEmail.putExtra(Intent.EXTRA_SUBJECT, "Nota do Curso");
			intentEmail.putExtra(Intent.EXTRA_TEXT, "Olá "+ alunoSelecionado.getNome() +", Sua nota foi: "+ alunoSelecionado.getNota().toString());
			break;
		
		case R.id.menu_contexto_enviarSMS:
			Intent intentSMS = new Intent(Intent.ACTION_VIEW);
			intentSMS.setData(Uri.parse("sms:"+ alunoSelecionado.getTelefone()));
			intentSMS.putExtra("sms_body", "Mensagem");
			item.setIntent(intentSMS);
			break;
		
		case R.id.menu_contexto_ligar:
			Intent intentLigar = new Intent(Intent.ACTION_CALL);
			intentLigar.setData(Uri.parse("tel:"+ alunoSelecionado.getTelefone()));
			item.setIntent(intentLigar);
			break;
		
		case R.id.menu_contexto_navegarNoSite:
			Intent intentSite = new Intent(Intent.ACTION_VIEW);
			intentSite.setData(Uri.parse("http:"+ alunoSelecionado.getSite()));
			item.setIntent(intentSite);
			break;
		
		default:
			break;
		}
		
		return false;
	}
	
	public void carregaLista(){
		 
		AlunoDAO dao = new AlunoDAO(ListaAlunosActivity.this);
		List<Aluno> alunos = dao.getList();
		dao.close();
		
		//String[] alunos = {"Anderson", "Felipe", "Guilherme"};
		
		ArrayAdapter<Aluno> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alunos);
		listaAlunos.setAdapter(adapter);
	}
}
