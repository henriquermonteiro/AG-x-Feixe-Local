/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utfpr.sisint.problema;

/**
 * Feixe MAX = 55;
 * @author henrique
 */
public enum ObjetosMochila {

    Obj1(3.0, 1.0),
    Obj2(8.0, 3.0),
    Obj3(12.0, 1.0),
    Obj4(2.0, 8.0),
    Obj5(8.0, 9.0),
    Obj6(4.0, 3.0),
    Obj7(4.0, 2.0),
    Obj8(5.0, 8.0),
    Obj9(1.0, 5.0),
    Obj10(1.0, 1.0),
    Obj11(8.0, 1.0),
    Obj12(6.0, 6.0),
    Obj13(4.0, 3.0),
    Obj14(3.0, 2.0),
    Obj15(3.0, 5.0),
    Obj16(5.0, 2.0),
    Obj17(7.0, 3.0),
    Obj18(3.0, 8.0),
    Obj19(5.0, 9.0),
    Obj20(7.0, 3.0),
    Obj21(4.0, 2.0),
    Obj22(3.0, 4.0),
    Obj23(7.0, 5.0),
    Obj24(2.0, 4.0),
    Obj25(3.0, 3.0),
    Obj26(5.0, 1.0),
    Obj27(4.0, 3.0),
    Obj28(3.0, 2.0);

    private final Double peso;
    private final Double valor;

    ObjetosMochila(Double peso, Double valor) {
        this.peso = peso;
        this.valor = valor;
    }

    public Double getPeso() {
        return peso;
    }

    public Double getValor() {
        return valor;
    }
    
    public static ObjetosMochila[] getList(){
        return new ObjetosMochila[]{Obj1, Obj2, Obj3, Obj4, Obj5, Obj6, Obj7, Obj8, Obj9, Obj10, Obj11, Obj12, Obj13, Obj14, Obj15, Obj16, Obj17, Obj18,
                                     Obj19, Obj20, Obj21, Obj22, Obj23, Obj24, Obj25, Obj26, Obj27, Obj28};
    }
}
