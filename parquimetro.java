
/**
 * Representa um parquímetro fracionado simplificado.
 * 
 * O parquímetro funciona assim:
 * 1. o cliente seleciona a quantidade de tempo
 *    de estacionamento, incrementando-o
 *    de 15 em 15 minutos.
 * 2. o cliente insere o dinheiro suficiente para
 *    pagar o ticket de estacionamento.
 * 3. o cliente pede para imprimir o ticket.
 * 
 * O parquímetro mantém o total de dinheiro acumulado
 * ao longo do tempo em que está em operação.
 */

 public class Parquimetro
 {
     // campo que armazena quanto custa cada unidade de tempo solicitada.
     // p. ex. o preço unitário a cada 15 minutos comprados.
     private float preçoUnitario_;
     // armazena o tempo minimo
     private int tempoMinimo_;
     // armazena o tempo maximo
     private int tempoMaximo_;
     // quantidade de dinheiro já inserida pelo cliente
     private float balanco_;
     // quantidade total de dinheiro acumulado por este parquímetro
     private float total_;
     // armazena o tempo solicitado atual.
     private int tempoSolicitado_;
     
     public class Horário 
     {
         private int hora_;
 
         private int minuto_;
 
         public Horário(int hora, int minuto)
         {
             hora_ = hora;
             minuto_ = minuto;
         }
     
         public void aumentarMinutos (int minutosAdicionados)
         {
             int total = 60 * hora_ + minuto_ + minutosAdicionados;
             hora_ = (total / 60) % 24;
             minuto_ = total % 60;
         }
     
         public String imprimirFormatado()
         {
             return "" + hora_ + ":" + minuto_;
         }
 
     }
 
     /**
      * Constroi um novo objeto Parquimetro com o preço unitário configurado.
      */
     public Parquimetro(float preçoDaFração, int tempoMinMinutos, int tempoMaxMinutos)
     {
         preçoUnitario_ = preçoDaFração;
         balanco_ = 0;
         total_ = 0; 
         tempoSolicitado_ = 0;
         tempoMinimo_ = tempoMinMinutos;  
         tempoMaximo_ = tempoMaxMinutos;
     }
 
     public void insefrirDinheiro(float quantia)
     {
         balanco_ = balanco_ + quantia;
     }
     
     public void aumentarTempo()
     {
         if(tempoSolicitado_ < 30 && (tempoSolicitado_ + 15 <= tempoMaximo_))
             tempoSolicitado_ = tempoSolicitado_ + 15;
         else if(tempoSolicitado_ + 30 <= tempoMaximo_)
             tempoSolicitado_ = tempoSolicitado_ + 30;
         else
             System.out.printf ("Nao e permitido a permanencia por mais de %d minutos \n", tempoMaximo_); 
     }
 
     public float esvaziar()
     {
         float moedas;
         moedas = total_;
         total_ = 0;
         System.out.printf("Estão sendo retiradas R$ %.02f \n", moedas);
         return moedas;
     }
 
     public void imprimirTicket(int horaAtual, int minutoAtual)
     {
         if (tempoSolicitado_ < tempoMinimo_) {
             System.out.printf("Tempo solicitado %d é menor que o tempo minimo %d. Adicione mais tempo.", tempoSolicitado_, tempoMinimo_);
         }
         else if(balanco_ >= this.getTotalAPagar()){
             Horário horário = new Horário(horaAtual, minutoAtual);
             System.out.println("+--------------------------+");
             System.out.println("| Ticket de Zona Azul      |");
             System.out.println("+--------------------------+");
             System.out.printf ("| Início: %s           |\n", horário.imprimirFormatado());
             System.out.println("|                          |");
             horário.aumentarMinutos(tempoSolicitado_);
             System.out.printf ("| Válido até: %s|\n", horário.imprimirFormatado());
             System.out.printf ("| Valor:R$ %-13s|\n", this.getTotalAPagar());
             balanco_ = balanco_ - this.getTotalAPagar();
             System.out.printf ("| Saldo restante disponivel:R$ %-13s|\n", balanco_);   
             System.out.println("+--------------------------+");
             total_ = total_ + this.getTotalAPagar();
             System.out.printf ("| Saldo restante disponivel:R$ %-13s|\n", this.devolverTroco());
             tempoSolicitado_ = 0;
         }
         else {
             System.out.println("+--------------------------+");
             System.out.println("Saldo inserido insuficiente");
             System.out.println("Insira mais R$" + (this.getTotalAPagar()-balanco_));
             System.out.println("+--------------------------+");
         }
         
     }
     
     public float getPrecoUnitario()
     {
         return preçoUnitario_;
     }
     
     public int getTempoSolicitado()
     {
         return tempoSolicitado_;
     }
     
     public float getBalanço()
     {
         return balanco_;
     }
     
     private float getTotalAPagar()
     {
         if (tempoSolicitado_ <= 30) {
             return ((tempoSolicitado_)/15)*preçoUnitario_;
         }
         return (2 + (tempoSolicitado_-30)/30)*preçoUnitario_;
     } 
     
     private float devolverTroco(){
         float troco = balanco_;
         balanco_ = 0;
         System.out.println("+--------------------------+");
         System.out.printf("Obrigado! Confira seu troco de R$ %.02f \n", troco);
         System.out.println("+--------------------------+");
         return troco;
     }
 }
 