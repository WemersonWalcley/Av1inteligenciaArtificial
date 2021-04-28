
package com.mycompany.algoritmogenetico;

public class Principal {
    private static double[] populacao;
    private static String[] binarios;
    private static int[] decimais;
    private static double[] fit;
    private static double taxaDeCrossover = 0.7;
    private static double taxaDeMutação = 0.01;
    private static int individuos = 400;
    private static int genes = 8;
    
    public static void main(String[] args) {
        gerarPopulacaoInicial();
        fit = new double[individuos];
        for (int j = 0; j < fit.length; j++){ fit[j] = fit(decimais[j]); /*System.out.println(fit[j]);*/}
        for (int i = 0; i < genes*individuos; i++) {
            int pai = paiMenor();
            int mae = maeMaior(pai);
            taxaDeCrossover(pai,mae);
            mutacao(pai,mae);
            for (int j = 0; j < individuos; j++) decimais[j] = binarioToDecimal(binarios[j]);
            for (int j = 0; j < fit.length; j++){ fit[j] = fit(decimais[j]); /*System.out.println(fit[j]);*/}
        }
        System.out.println("Resultado do cruzamento genético");
        System.out.println("(x) = "+decimais[paiMaior()]);
        System.out.println("f(x) = "+fit(decimais[paiMaior()]));
    }
    
    public static void gerarPopulacaoInicial() {
        int espaco = individuos*genes, cont = 0;
        populacao = new double[espaco];
        decimais = new int[individuos];
        binarios = new String[individuos];
        for (int i = 0; i < espaco; i++) {
            populacao[i] = Math.random();
        }
        
        for (int i = 0; i < individuos; i++) {
            String binario = "";
            for (int j = 0; j < genes; j++) {
                binario += (int) Math.round(populacao[cont]);
                cont ++;
                binarios[i] = binario;
            }
            decimais[i] = binarioToDecimal(binario);
        }
    }
    
    public static int binarioToDecimal(String binario) {
        char[] corrente1 = binario.toCharArray();
        char[] corrente = new char[corrente1.length];
        for (int i = 0; i < corrente1.length; i++) corrente[corrente1.length-i-1] = corrente1[i];
        int valor = 0, resultado = 0, aux = 0;
        for (int i = 0; i < corrente.length; i++) {
            valor = aux * 2;
            if (valor == 0) valor = 1;
            if (corrente[i] == '1') {
                resultado += valor;
            }
            aux = valor;
        }
        return resultado;
    }
    
    public static double fit(int x) {
        
        return (x*2)-(12*x)+16; //Função pedida na questão
    
    }
    
    public static int paiMaior() {
        double maior = fit[0];
        int posicao = 0;
        for (int i = 1; i < fit.length; i++) {
            if (fit[i] > maior){
                maior = fit[i];
                posicao = i;
            }
        }
        return posicao;
    }
    
    public static int maeMaior(double pai) {
        double maior = 0; int posicao = 0;
        if (pai == 0) maior = fit[1];
        else maior = fit[0];
        for (int i = 0; i < fit.length; i++) {
            if (fit[i] > maior && i != pai) {
                maior = fit[i];
                posicao = i;
            }
        }
        return posicao;
    }
    
    public static int paiMenor() {
        double menor = fit[0];
        int posicao = 0;
        for (int i = 1; i < fit.length; i++) {
            if (fit[i] < menor){
                menor = fit[i];
                posicao = i;
            }
        }
        return posicao;
    }
    
    public static int maeMenor(double pai) {
        double menor = 0; int posicao = 0;
        if (pai == 0) menor = fit[1];
        else menor = fit[0];
        for (int i = 0; i < fit.length; i++) {
            if (fit[i] < menor && i != pai) {
                menor = fit[i];
                posicao = i;
            }
        }
        return posicao;
    }
    
    public static void taxaDeCrossover(int posicaoPai, int posicaoMae) {
        if (Math.random() < taxaDeCrossover) {
            char[] binarioPai = binarios[posicaoPai].toCharArray();
            char[] binarioMae = binarios[posicaoMae].toCharArray();
            String corrente = "";
            int taxaDeCrossover = Math.round(binarioPai.length/2);
            for (int i = 0; i < taxaDeCrossover; i++) corrente += binarioPai[i];
            taxaDeCrossover = binarioMae.length - taxaDeCrossover;
            for (int i = taxaDeCrossover; i < binarioMae.length; i++) corrente += binarioMae[i];
            binarios[0] = corrente;
        }

    }
    
    public static void mutacao(int posicaoPai, int posicaoMae) {
        if (Math.random() < taxaDeMutação) {
            char[] binarioPai = binarios[posicaoPai].toCharArray();
            int a = (binarioPai.length) -1;
            int posicao = (int) Math.round(Math.random() * a);
            if (binarioPai[posicao] == '0') binarioPai[posicao] = '1'; else binarioPai[posicao] = '0';
            binarios[1] = String.valueOf(binarioPai);
            }
        if (Math.random() < taxaDeMutação) {
            char[] binarioMae = binarios[posicaoMae].toCharArray();
            int a = (binarioMae.length) -1;
            int posicao = (int) Math.round(Math.random() * a);
            if (binarioMae[posicao] == '0') binarioMae[posicao] = '1'; else binarioMae[posicao] = '0';
            binarios[2] = String.valueOf(binarioMae);
            
        }
        
    }
}
