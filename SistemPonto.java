package ControleDePonto;

public class SistemPonto {
	public SistemaPonto() {
	    this.gerenciador = new GerenciadorColaboradores();
	}
	
	public void cadastrarColaborador(Colaborador c) {
		gerenciador.adicionarColaborador(c);
	}
}
