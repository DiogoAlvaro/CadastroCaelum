package br.com.caelum.cadastro;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import br.com.caelum.cadastro.dao.AlunoDAO;
import br.com.caelum.modelo.Aluno;
import br.com.caelum.modelo.Extras;

public class FormularioActivity extends ActionBarActivity {

	private FormularioHelper helper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.formulario);
		
		Button botao = (Button) findViewById(R.id.botao);
		Aluno aluno = (Aluno) getIntent().getSerializableExtra(Extras.ALUNO_SELECIONADO);
		helper = new FormularioHelper(this);
		
		
		if (aluno == null){
			Log.i("Formulário", "Novo Cadastro de Aluno");
			aluno = new Aluno();
		} else {
			Log.i("Formulário", "É uma alteração. O aluno "+ aluno.getNome() +" veio na Intent");
			helper.colocarNoFormulario(aluno);
			botao.setText("Alterar");
		}
		
		
		
		
		botao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Aluno aluno = helper.PegaAlunoDoFormulario();
				AlunoDAO dao = new AlunoDAO(FormularioActivity.this);
				
				if (aluno.getId() != null){
					dao.alterar(aluno);
				} else {
					dao.insere(aluno);
				}
				dao.close();
				
				Toast.makeText(FormularioActivity.this, "Cadastro Efetuado com sucesso!", Toast.LENGTH_LONG).show();
				
				finish();
				

			}
		});
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.formulario, menu);
		return true;
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
