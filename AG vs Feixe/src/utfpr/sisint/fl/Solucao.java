/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utfpr.sisint.fl;

import utfpr.sisint.problema.ObjetosMochila;
import utfpr.sisint.problema.ProblemConstants;

/**
 *
 * @author henrique
 */
public class Solucao {
    private Byte[] descricao;

    public Solucao(Byte[] descricao) {
        this.descricao = descricao;
    }

    public Solucao(String descriptor) {
        descriptor = descriptor.trim();
        
        if(descriptor.length() != ProblemConstants.DESCRIPTION_LENGHT){
            throw new IllegalArgumentException("The stringt has the wrong size.");
        }
        
        Byte[] desc = new Byte[((Double)Math.ceil(ProblemConstants.DESCRIPTION_LENGHT / 8)).intValue()];
        int position = 0;
        int count = 0;
        byte buffer = 0;
        
        for(char c : descriptor.toCharArray()){
            switch(c){
                case '1':
                    buffer = (byte) (buffer >> 1);
                    buffer = (byte) (buffer | 0x10000000);
                    break;
                case '0':
                    buffer = (byte) (buffer >> 1);
//                    buffer = (byte) (buffer | 0x00000000);
                    break;
                default:
                    throw new IllegalArgumentException("The string " + descriptor + ", doesn't respect the definition: (0*1*)*.");
            }
            
            count ++;
            
            if(count > 7){
                desc[position] = buffer;
                
                position++;
                
                count = 0;
            }
        }
        
        this.descricao = desc;
    }
    
    public Solucao[] getSucessores(){
        Solucao[] sucessores = new Solucao[ProblemConstants.DESCRIPTION_LENGHT.intValue()];
        
        for(int k = 0; k < ProblemConstants.DESCRIPTION_LENGHT; k++){
            
        }
        
        return sucessores;
    }
    
    public Double getFitness(){
        byte buffer = descricao[0];
        byte position = 1;
        byte count = 0;
        
        Double fitness = 0.0;
        Double peso_cap = 0.0;
        
        for(ObjetosMochila obj : ObjetosMochila.getList()){
            if(buffer << 1 == 0x00000001){
                fitness += obj.getValor();
                peso_cap += obj.getPeso();
            }
            
            count++;
            
            if(count > 7){
                buffer = descricao[position];
                position ++;
                count = 0;
            }
        }
        
        if(peso_cap > ProblemConstants.CAP){
            fitness /= 2;
            fitness /= (peso_cap / ProblemConstants.CAP);
        }
        
        return fitness;
    }

    @Override
    public String toString() {
        byte buffer = descricao[0];
        byte position = 1;
        byte count = 0;
        
        String desc = "";
        
        for(int k = 0; k < ProblemConstants.DESCRIPTION_LENGHT; k++){
            if(buffer << 1 == 0x00000001){
                desc = desc.concat("1");
            }else{
                desc = desc.concat("0");
            }
            
            buffer = (byte)(buffer << 1);
            count++;
            
            if(count > 7){
                buffer = descricao[position];
                position ++;
                count = 0;
            }
        }
        
        
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
