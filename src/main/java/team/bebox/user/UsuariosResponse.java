package team.bebox.user;

import java.util.List;

public class UsuariosResponse {
	List<Usuario> alunos;
	int next;
	int prev;
	int total;
	public UsuariosResponse(List<Usuario> alunos, int next, int prev, int total) {
		this.alunos = alunos;
		this.next = next;
		this.prev = prev;
		this.total = total;
	}
	public List<Usuario> getAlunos() {
		return alunos;
	}
	public void setAlunos(List<Usuario> alunos) {
		this.alunos = alunos;
	}
	public int getNext() {
		return next;
	}
	public void setNext(int next) {
		this.next = next;
	}
	public int getPrev() {
		return prev;
	}
	public void setPrev(int prev) {
		this.prev = prev;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}	
	
}
