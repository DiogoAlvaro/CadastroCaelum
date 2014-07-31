package br.com.caelum.cadastro;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import br.com.caelum.modelo.Aluno;

public class FormularioHelper {

	private Aluno aluno;
	private ImageView foto;
	private EditText nome;
	private EditText telefone;
	private EditText endereco;
	private EditText site;
	private SeekBar nota;
	
	public FormularioHelper(FormularioActivity activity) {
		aluno = new Aluno();
		
		foto = (ImageView) activity.findViewById(R.id.foto);
		nome = (EditText) activity.findViewById(R.id.nome);
		telefone = (EditText) activity.findViewById(R.id.telefone);
		endereco = (EditText) activity.findViewById(R.id.endereco);
		site = (EditText) activity.findViewById(R.id.site);
		nota = (SeekBar) activity.findViewById(R.id.nota);
	}
	
	
	public Aluno PegaAlunoDoFormulario(){
		aluno.setNome(nome.toString());
		aluno.setTelefone(telefone.toString());
		aluno.setEndereco(endereco.toString());
		aluno.setSite(site.toString());
		aluno.setNota(Double.valueOf(nota.getProgress()));
		
		return aluno;
	}
	
}
