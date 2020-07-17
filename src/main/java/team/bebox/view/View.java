package team.bebox.view;

/**
 * Esta classe define as diferentes visualizacoes disponiveis para serializacoes
 */
public class View {
	
	/**
	 * Visualizacao principal com os principais atributos
	 */
	public static class UsuarioMinimo {}
	public static class UsuarioBase extends UsuarioMinimo{}
	public static class UsuarioResumo extends UsuarioBase{}
	
	
	/**
	 * Visualizacao com todos os atributos
	 * Inclui tudos os atributos marcados com Main
	 */
	public static class UsuarioCompleto extends UsuarioResumo {}
	public static class UsuarioView extends UsuarioCompleto{}
	
	/**
	 * Visualizacao alternativa
	 */
	public static class UsuarioResumoAlternativo {}
	public static class Admin extends UsuarioResumo{}
	
	public static class Pagamento {}
}
