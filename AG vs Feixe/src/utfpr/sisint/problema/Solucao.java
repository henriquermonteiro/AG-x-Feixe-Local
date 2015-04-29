/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utfpr.sisint.problema;

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
        
        while(descriptor.length() != desc.length * 8){
            descriptor = descriptor.concat("0");
        }
        
        for(char c : descriptor.toCharArray()){
            switch(c){
                case '1':
                    buffer = (byte) (buffer << 1);
                    buffer = (byte) (buffer + 0x1);
                    break;
                case '0':
                    buffer = (byte) (buffer << 1);
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
            char[] desc = toString().toCharArray();
            
            if(desc[k] == '0'){
                desc[k] = '1';
            }else{
                desc[k] = '0';
            }
            
            sucessores[k] = new Solucao(new String(desc));
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
            if(buffer < 0){
                fitness += obj.getValor();
                peso_cap += obj.getPeso();
            }
            
            buffer = (byte) (buffer << 1);
            count++;
            
            if(count > 7){
                buffer = descricao[position];
                position ++;
                count = 0;
            }
        }
        
        if(peso_cap > ProblemConstants.CAP){
            fitness /= 10;
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
            if(buffer < 0){
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
        
        
        return desc;
    }
    
    public Solucao mutatePosition(Integer position){
        String desc = toString();
        
        char[] description = desc.toCharArray();
        
        if(position >= 0 && position < ProblemConstants.DESCRIPTION_LENGHT){
            if(description[position] == '0'){
                description[position] = '1';
            }else{
                description[position] = '0';
            }
            
            return new Solucao(new String(description));
        }
        
        throw new IllegalArgumentException("A posição informada não é válida.");
    }
    
    public Solucao[] crossover(Solucao mate, Integer position){
        Solucao[] solutions = new Solucao[2];
        
        if(position < 0 && position >= ProblemConstants.DESCRIPTION_LENGHT){
            throw new IllegalArgumentException("A posição informada não é válida.");
        }
        
        if(mate == null){
            throw new NullPointerException("O parâmetro 'mate' não pode ser nulo.");
        }
        
        String desc1 = toString();
        String desc2 = mate.toString();
        
        String desc1_pt1 = desc1.substring(0, position);
        String desc1_pt2 = desc1.substring(position, desc1.length());
        String desc2_pt1 = desc2.substring(0, position);
        String desc2_pt2 = desc2.substring(position, desc2.length());
        
        solutions[0] = new Solucao(desc1_pt1.concat(desc2_pt2));
        solutions[1] = new Solucao(desc2_pt1.concat(desc1_pt2));
        
        return solutions;
    }
}
