package utfpr.dainf.ct.ed.exemplo;


import java.util.LinkedList;
import java.util.Stack;

/**
 * UTFPR - Universidade Tecnológica Federal do Paraná
 * DAINF - Departamento Acadêmico de Informática
 * 
 * Exemplo de implementação de árvore binária.
 * @author Wilson Horstmeyer Bogado <wilson@utfpr.edu.br>
 * @param <E> O tipo do valor armazenado nos nós na árvore
 */
public class ArvoreBinaria<E> {
    
    private E dado;
    private ArvoreBinaria<E> esquerda;
    private ArvoreBinaria<E> direita;
    
    // para percurso iterativo
    private boolean inicio = true;
    private Stack<ArvoreBinaria<E>> pilha;
    private ArvoreBinaria<E> ultimoVisitado;

    /**
     * Cria uma árvore binária com dado nulo na raiz.
     */
    public ArvoreBinaria() {
    }

    /**
     * Cria uma árvore binária com dado {@code dado} na raiz.
     * @param valor O dado do nó raiz
     */
    public ArvoreBinaria(E dado) {
        this.dado = dado;
    }
    
    /**
     * Adiciona um nó à esquerda do nó corrente.
     * @param dado O dado associado ao nó inserido.
     * @return A árvore adicionada ao nó
     */
    public ArvoreBinaria<E> insereEsquerda(E dado) {
        ArvoreBinaria<E> e = esquerda;
        esquerda = new ArvoreBinaria<>(dado);
        esquerda.esquerda = e;
        return esquerda;
    }
    
    /**
     * Adiciona um nó à esquerda do nó corrente.
     * @param dado O dado associado ao nó inserido.
     * @return A árvore adicionada ao nó
     */
    public ArvoreBinaria<E> insereDireita(E dado) {
        ArvoreBinaria<E> d = direita;
        direita = new ArvoreBinaria<>(dado);
        direita.direita = d;
        return direita;
    }
    
    /**
     * Implementação padrão que exibe o dado armazenado no nó usando
     * o método {@code toString() }.
     * Pode ser sobrecarregado em classes derivadas para implementar outras
     * formas de visita.
     * @param no O nó a ser visitado
     */
    protected void visita(ArvoreBinaria<E> no) {
        System.out.print(" " + no.dado);
    }
    
    /**
     * Visita os nós da subárvore em-ordem.
     * @param raiz A raiz da subárvore
     */
    public void visitaEmOrdem(ArvoreBinaria<E> raiz) {
        if (raiz != null) {
            ArvoreBinaria.this.visitaEmOrdem(raiz.esquerda);
            visita(raiz);
            ArvoreBinaria.this.visitaEmOrdem(raiz.direita);
        }
    }
    
    /**
     * Visita os nós da árvore em-ordem a partir da raiz.
     */
    public void visitaEmOrdem() {
        visitaEmOrdem(this);
    }
    
    
    //----------------------------------------------------------------------------------------------------------------------------------

    // Visita pre ordem recursivo
    public void visitaPreOrdem()
    {
        visitaPreOrdem(this);
    }

    public void visitaPreOrdem(ArvoreBinaria<E> a)
    {
        visita(a);
        if (a != null)
        {
            if (a.esquerda != null)
                visitaPreOrdem(a.esquerda);

            if (a.direita != null)
                visitaPreOrdem(a.direita);
        }
    }


    // Visita pre ordem iterativo
    public ArvoreBinaria<E> proximoPreOrdem()
    {
        ArvoreBinaria<E> resultado;
        resultado = null;
     
        if (inicio)
        {
            reinicia();
            pilha.push(this);
            inicio = false;
        }
        
        if (!pilha.isEmpty())
        {
            resultado = pilha.pop();
            
            if (resultado.direita != null)
                pilha.push(resultado.direita);
            if (resultado.esquerda != null)
                pilha.push(resultado.esquerda);
        }

        if (resultado == null)
        {
            inicio = true;
            tiraVisitado(this);
        }

        return resultado;
    }

//----------------------------------------------------------------------------------------------------------------------------------
    // Visita pos ordem recursivo
    public void visitaPosOrdem() { visitaPosOrdem(this); }

    public void visitaPosOrdem(ArvoreBinaria<E> no)
    {
        if (no.esquerda != null)
            visitaPosOrdem(no.esquerda);
        if (no.direita != null)
            visitaPosOrdem(no.direita);
        visita(no);
    }

    // Visita pos ordem iterativo
    public ArvoreBinaria<E> proximoPosOrdem()
    {
        ArvoreBinaria<E> resultado, pai = null;
        resultado = this;

        while (resultado != null)
        {
            if (resultado.visitado == false)
            {
                pai = resultado;
                resultado = resultado.esquerda;
                if (resultado == null && pai != null)
                    resultado = pai.direita;
            }
            else
            {
                if (pai != null)
                   resultado = pai.direita;   
                if (resultado != null && resultado.visitado == true)
                    break;
            }
        }

        resultado = pai;
        
        if (resultado == null)
        {
            inicio = true;
            tiraVisitado(this);
        }

        else
            resultado.visitado = true;

        return resultado;
    }


    private void tiraVisitado(ArvoreBinaria<E> a)
    {
        if (a != null)
        {
            a.visitado = false;
            tiraVisitado(a.esquerda);
            tiraVisitado(a.direita);
        }
    }


//----------------------------------------------------------------------------------------------------------------------------------

    //Visita em nível iterativo
    public ArvoreBinaria<E> proximoEmNivel()
    {
        ArvoreBinaria<E> resultado;
        if (fila == null)
            fila = new LinkedList<>();
        if (inicio)
        {
            inicio = false;
            fila.add(this);
        }


        resultado = fila.pollFirst();
        
        if (resultado != null)
        {                
            if (resultado.esquerda != null)
                fila.add(resultado.esquerda);
            if (resultado.direita != null)
                fila.add(resultado.direita);
        }


        if (resultado == null)
        {
            tiraVisitado(this);
            inicio = true;
        }

        return resultado;
    } 

    
    private void inicializaPilha() {
        if (pilha == null) {
            pilha = new Stack<>();
        }
    }
    
    /**
     * Reinicia o percurso a partir do início.
     * Deve ser chamado após percorrer toda a árvore para realizar novo
     * percurso ou para voltar ao início a qualquer momento.
     */
    public void reinicia() {
        inicializaPilha();
        pilha.clear();
        ultimoVisitado = this;
        inicio = true;
    }
    
    /**
     * Retorna o dado do próximo nó em-ordem.
     * @return O dado do próximo nó em-ordem.
     */
    public ArvoreBinaria<E> proximoEmOrdem() {
        ArvoreBinaria<E> resultado = null;
        if (inicio) {
            reinicia();
            inicio = false;
        }
        if (!pilha.isEmpty() || ultimoVisitado != null) {
            while (ultimoVisitado != null) {
                pilha.push(ultimoVisitado);
                ultimoVisitado = ultimoVisitado.esquerda;
            }
            ultimoVisitado = pilha.pop();
            resultado = ultimoVisitado;
            ultimoVisitado = ultimoVisitado.direita;
        }
        return resultado;
    }
    
    /**
     * Retorna o dado armazenado no nó.
     * @return O dado armazenado no nó.
     */
    public E getDado() {
        return dado;
    }

    /**
     * Atribui um dado ao nó.
     * @param dado O dado a ser atribuído ao nó.
     */
    public void setDado(E dado) {
        this.dado = dado;
    }

    /**
     * Retorna a árvore esqueda.
     * @return A árvore esquerda.
     */
    protected ArvoreBinaria<E> getEsquerda() {
        return esquerda;
    }

    /**
     * Retorna a árvore direita.
     * @return A árvore direita.
     */
    protected ArvoreBinaria<E> getDireita() {
        return direita;
    }
    
}
