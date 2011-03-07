/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package me.chester;

import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;
import javax.wireless.messaging.MessageConnection;
import javax.wireless.messaging.TextMessage;

/**
 * @author chester
 */
public class SMSDenuncia extends MIDlet implements CommandListener, Runnable {

    private static final int MEIO_CPTM = 0;

    private static final int MEIO_METRO = 1;
    
    private static final int DENTRO_TREM = 0;

    private static final int DENTRO_ESTACAO = 1;

    private static final String[][] LINHAS = {
                    {"7-Rubi", "8-Diamante", "9-Esmeralda", "10-Turquesa", "11-Coral", "12-Safira"},
                    {"1-Azul", "2-Verde", "3-Vermelha", "4-Amarela", "5-Lilás"}
                };

    private static final String[][] ESTACOES = {
        {},
        {"Jabaquara", "Conceição", "São Judas", "Saúde", "Praça da Árvore", "Santa Cruz", "Vila Mariana", "Ana Rosa", "Paraíso", "Vergueiro", "São Joaquim", "Liberdade", "Sé", "São Bento", "Luz", "Tiradentes", "Armênia", "Portuguesa-Tietê", "Carandiru", "Santana", "Jardim São Paulo", "Parada Inglesa", "Tucuruvi"},
        {"Vila Prudente", "Tamanduateí", "Sacomã", "Alto do Ipiranga", "Santos-Imigrantes", "Chácara Klabin", "Ana Rosa", "Paraíso", "Brigadeiro", "Trianon-Masp", "Consolação", "Clínicas", "Sumaré", "Vila Madalena"},
        {"Corinthians-Itaquera", "Artur Alvim", "Patriarca", "Guilhermina-Esperança", "Vila Matilde", "Penha", "Carrão", "Tatuapé", "Belém", "Bresser-Mooca", "Brás", "Pedro II", "Sé", "Anhangabaú", "República", "Santa Cecília", "Marechal Deodoro", "Palmeiras-Barra Funda"},
        {"Faria Lima", "Paulista"},
        {"Capão Redondo", "Campo Limpo", "Vila das Belezas", "Giovanni Gronchi", "Santo Amaro", "Largo Treze"},
        {},
        {"Luz", "Palmeiras/Barra Funda", "Água Branca", "Lapa", "Piqueri", "Pirituba", "Vila Clarice", "Jaraguá", "Perus", "Caieiras", "Franco da Rocha", "Baltazar Fidelis", "Francisco Morato", "Botujuru", "Campo Limpo Paulista", "Várzea Paulista", "Jundiaí"},
        {"Júlio Prestes", "Palmeiras/Barra Funda", "Lapa", "Domingos de Moraes", "Imperatriz Leopoldina", "Presidente Altino", "Osasco", "Comandante Sampaio", "Quitaúna", "General Miguel Costa", "Carapicuiba", "Santa Terezinha", "Antonio João", "Barueri", "Jardim Belval", "Jardim Silveira", "Jandira", "Sagrado Coração", "Engº. Cardoso", "Itapevi"},
        {"Osasco", "Presidente Altino", "Ceasa", "Villa-Lobos-Jaguaré", "Cidade Universitária", "Pinheiros", "Hebraica-Rebouças", "Cidade Jardim", "Vila Olímpia", "Berrini", "Morumbi", "Granja Julieta", "Santo Amaro", "Socorro", "Jurubatuba", "Autódromo", "Primavera-Interlagos", "Grajaú"},
        {"Luz", "Brás", "Mooca", "Ipiranga", "Tamanduateí", "São Caetano", "Utinga", "Prefeito Saladino", "Pref Celso Daniel Sto André", "Capuava", "Mauá", "Guapituba", "Ribeirão Pires", "Rio Grande da Serra"},
        {"Luz", "Brás", "Tatuapé", "Corinthians/Itaquera", "Dom Bosco", "José Bonifácio", "Guaianazes", "Antonio Gianetti Neto", "Ferraz de Vasconcelos", "Poá", "Calmon Viana", "Suzano", "Jundiapeba", "Brás Cubas", "Mogi das Cruzes", "Estudantes"},
        {"Brás", "Tatuapé", "Engº. Goulart", "USP-Leste", "Comendador Ermelino", "São Miguel Paulista", "Jard. Helena-Vila Mara", "Itaím Paulista", "Jardim Romano", "Engº. Manoel Feio", "Itaquaquecetuba", "Aracaré", "Calmon Viana"}
    };

    /**
     * Forms na ordem que têm que aparecer
     */
    private Displayable[] FORMS = {getFormTipo(), getFormDentro(), getFormMeio(), getFormLinha(), getFormEstacao(), getFormSentido(), getFormCarro(), getTextBoxSMS()};

    /**
     * Campos de pergunta na ordem em que estão no array FORMS
     */
    private ChoiceGroup[] CAMPOS = {getChoiceGroupTipo(), getChoiceGroupDentro(), getChoiceGroupMeio(), getChoiceGroupLinha(), getChoiceGroupEstacao(), getChoiceGroupSentido(), null, null};



    private boolean midletPaused = false;

    //<editor-fold defaultstate="collapsed" desc=" Generated Fields ">//GEN-BEGIN:|fields|0|
    private Command exitCommand;
    private Command backCommand;
    private Command okCommand;
    private Command enviarCommand;
    private Form formTipo;
    private ChoiceGroup choiceGroupTipo;
    private Form formMeio;
    private ChoiceGroup choiceGroupMeio;
    private Form formEstacao;
    private ChoiceGroup choiceGroupEstacao;
    private Form formLinha;
    private ChoiceGroup choiceGroupLinha;
    private Form formDentro;
    private ChoiceGroup choiceGroupDentro;
    private Form formSentido;
    private ChoiceGroup choiceGroupSentido;
    private Form formCarro;
    private TextField textFieldCarro;
    private StringItem stringItem;
    private TextBox textBoxSMS;
    //</editor-fold>//GEN-END:|fields|0|

    /**
     * The HelloMIDlet constructor.
     */
    public SMSDenuncia() {
    }

    //<editor-fold defaultstate="collapsed" desc=" Generated Methods ">//GEN-BEGIN:|methods|0|
    //</editor-fold>//GEN-END:|methods|0|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: initialize ">//GEN-BEGIN:|0-initialize|0|0-preInitialize
    /**
     * Initilizes the application.
     * It is called only once when the MIDlet is started. The method is called before the <code>startMIDlet</code> method.
     */
    private void initialize() {//GEN-END:|0-initialize|0|0-preInitialize
        // write pre-initialize user code here
//GEN-LINE:|0-initialize|1|0-postInitialize
        // write post-initialize user code here
    }//GEN-BEGIN:|0-initialize|2|
    //</editor-fold>//GEN-END:|0-initialize|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: startMIDlet ">//GEN-BEGIN:|3-startMIDlet|0|3-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Started point.
     */
    public void startMIDlet() {//GEN-END:|3-startMIDlet|0|3-preAction
        // write pre-action user code here
        switchDisplayable(null, getFormTipo());//GEN-LINE:|3-startMIDlet|1|3-postAction
        // write post-action user code here
        Thread t = new Thread(this);
        t.start();
    }//GEN-BEGIN:|3-startMIDlet|2|
    //</editor-fold>//GEN-END:|3-startMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: resumeMIDlet ">//GEN-BEGIN:|4-resumeMIDlet|0|4-preAction
    /**
     * Performs an action assigned to the Mobile Device - MIDlet Resumed point.
     */
    public void resumeMIDlet() {//GEN-END:|4-resumeMIDlet|0|4-preAction
        // write pre-action user code here
//GEN-LINE:|4-resumeMIDlet|1|4-postAction
        // write post-action user code here
    }//GEN-BEGIN:|4-resumeMIDlet|2|
    //</editor-fold>//GEN-END:|4-resumeMIDlet|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: switchDisplayable ">//GEN-BEGIN:|5-switchDisplayable|0|5-preSwitch
    /**
     * Switches a current displayable in a display. The <code>display</code> instance is taken from <code>getDisplay</code> method. This method is used by all actions in the design for switching displayable.
     * @param alert the Alert which is temporarily set to the display; if <code>null</code>, then <code>nextDisplayable</code> is set immediately
     * @param nextDisplayable the Displayable to be set
     */
    public void switchDisplayable(Alert alert, Displayable nextDisplayable) {//GEN-END:|5-switchDisplayable|0|5-preSwitch
        // write pre-switch user code here
        Display display = getDisplay();//GEN-BEGIN:|5-switchDisplayable|1|5-postSwitch
        if (alert == null) {
            display.setCurrent(nextDisplayable);
        } else {
            display.setCurrent(alert, nextDisplayable);
        }//GEN-END:|5-switchDisplayable|1|5-postSwitch
        // write post-switch user code here
    }//GEN-BEGIN:|5-switchDisplayable|2|
    //</editor-fold>//GEN-END:|5-switchDisplayable|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Method: commandAction for Displayables ">//GEN-BEGIN:|7-commandAction|0|7-preCommandAction
    /**
     * Called by a system to indicated that a command has been invoked on a particular displayable.
     * @param command the Command that was invoked
     * @param displayable the Displayable where the command was invoked
     */
    public void commandAction(Command command, Displayable displayable) {//GEN-END:|7-commandAction|0|7-preCommandAction
        // write pre-action user code here
        if (displayable == formCarro) {//GEN-BEGIN:|7-commandAction|1|92-preAction
            if (command == backCommand) {//GEN-END:|7-commandAction|1|92-preAction
                // write pre-action user code here
                doBack();//GEN-LINE:|7-commandAction|2|92-postAction
                        // write post-action user code here
            } else if (command == okCommand) {//GEN-LINE:|7-commandAction|3|125-preAction
                // write pre-action user code here
                doMontaSMS();//GEN-LINE:|7-commandAction|4|125-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|5|81-preAction
        } else if (displayable == formDentro) {
            if (command == backCommand) {//GEN-END:|7-commandAction|5|81-preAction
                // write pre-action user code here
                doBack();//GEN-LINE:|7-commandAction|6|81-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|7|90-preAction
        } else if (displayable == formEstacao) {
            if (command == backCommand) {//GEN-END:|7-commandAction|7|90-preAction
                // write pre-action user code here
                doBack();//GEN-LINE:|7-commandAction|8|90-postAction
                        // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|9|67-preAction
        } else if (displayable == formLinha) {
            if (command == backCommand) {//GEN-END:|7-commandAction|9|67-preAction
                // write pre-action user code here
                doBack();//GEN-LINE:|7-commandAction|10|67-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|11|58-preAction
        } else if (displayable == formMeio) {
            if (command == backCommand) {//GEN-END:|7-commandAction|11|58-preAction
                // write pre-action user code here
                doBack();//GEN-LINE:|7-commandAction|12|58-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|13|91-preAction
        } else if (displayable == formSentido) {
            if (command == backCommand) {//GEN-END:|7-commandAction|13|91-preAction
                // write pre-action user code here
                doBack();//GEN-LINE:|7-commandAction|14|91-postAction
                        // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|15|63-preAction
        } else if (displayable == formTipo) {
            if (command == exitCommand) {//GEN-END:|7-commandAction|15|63-preAction
                // write pre-action user code here
                exitMIDlet();//GEN-LINE:|7-commandAction|16|63-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|17|118-preAction
        } else if (displayable == textBoxSMS) {
            if (command == backCommand) {//GEN-END:|7-commandAction|17|118-preAction
                // write pre-action user code here
                doBack();//GEN-LINE:|7-commandAction|18|118-postAction
                // write post-action user code here
            } else if (command == enviarCommand) {//GEN-LINE:|7-commandAction|19|115-preAction
                // write pre-action user code here
                doSend();//GEN-LINE:|7-commandAction|20|115-postAction
                // write post-action user code here
            }//GEN-BEGIN:|7-commandAction|21|7-postCommandAction
        }//GEN-END:|7-commandAction|21|7-postCommandAction
        // write post-action user code here
    }//GEN-BEGIN:|7-commandAction|22|
    //</editor-fold>//GEN-END:|7-commandAction|22|




    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: exitCommand ">//GEN-BEGIN:|18-getter|0|18-preInit
    /**
     * Returns an initiliazed instance of exitCommand component.
     * @return the initialized component instance
     */
    public Command getExitCommand() {
        if (exitCommand == null) {//GEN-END:|18-getter|0|18-preInit
            // write pre-init user code here
            exitCommand = new Command("Sair", Command.EXIT, 0);//GEN-LINE:|18-getter|1|18-postInit
            // write post-init user code here
        }//GEN-BEGIN:|18-getter|2|
        return exitCommand;
    }
    //</editor-fold>//GEN-END:|18-getter|2|
    //</editor-fold>
    //</editor-fold>








    //</editor-fold>








    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formTipo ">//GEN-BEGIN:|44-getter|0|44-preInit
    /**
     * Returns an initiliazed instance of formTipo component.
     * @return the initialized component instance
     */
    public Form getFormTipo() {
        if (formTipo == null) {//GEN-END:|44-getter|0|44-preInit
            // write pre-init user code here
            formTipo = new Form("SMS-Den\u00FAncia", new Item[] { getChoiceGroupTipo() });//GEN-BEGIN:|44-getter|1|44-postInit
            formTipo.addCommand(getExitCommand());
            formTipo.setCommandListener(this);//GEN-END:|44-getter|1|44-postInit
            // write post-init user code here
        }//GEN-BEGIN:|44-getter|2|
        return formTipo;
    }
    //</editor-fold>//GEN-END:|44-getter|2|
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: choiceGroupTipo ">//GEN-BEGIN:|45-getter|0|45-preInit
    /**
     * Returns an initiliazed instance of choiceGroupTipo component.
     * @return the initialized component instance
     */
    public ChoiceGroup getChoiceGroupTipo() {
        if (choiceGroupTipo == null) {//GEN-END:|45-getter|0|45-preInit
            // write pre-init user code here
            choiceGroupTipo = new ChoiceGroup("Selecione o tipo de den\u00FAcia:", Choice.MULTIPLE);//GEN-BEGIN:|45-getter|1|45-postInit
            choiceGroupTipo.append("Com\u00E9rcio Irregular", null);
            choiceGroupTipo.append("Delito", null);
            choiceGroupTipo.append("Vandalismo", null);
            choiceGroupTipo.append("Som Alto", null);
            choiceGroupTipo.append("Outras", null);
            choiceGroupTipo.setLayout(ImageItem.LAYOUT_DEFAULT);
            choiceGroupTipo.setSelectedFlags(new boolean[] { false, false, false, false, false });//GEN-END:|45-getter|1|45-postInit
            // write post-init user code here
        }//GEN-BEGIN:|45-getter|2|
        return choiceGroupTipo;
    }
    //</editor-fold>//GEN-END:|45-getter|2|



    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formMeio ">//GEN-BEGIN:|51-getter|0|51-preInit
    /**
     * Returns an initiliazed instance of formMeio component.
     * @return the initialized component instance
     */
    public Form getFormMeio() {
        if (formMeio == null) {//GEN-END:|51-getter|0|51-preInit
            // write pre-init user code here
            formMeio = new Form("SMS-Den\u00FAncia", new Item[] { getChoiceGroupMeio() });//GEN-BEGIN:|51-getter|1|51-postInit
            formMeio.addCommand(getBackCommand());
            formMeio.setCommandListener(this);//GEN-END:|51-getter|1|51-postInit
            // write post-init user code here
        }//GEN-BEGIN:|51-getter|2|
        return formMeio;
    }
    //</editor-fold>//GEN-END:|51-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: choiceGroupMeio ">//GEN-BEGIN:|52-getter|0|52-preInit
    /**
     * Returns an initiliazed instance of choiceGroupMeio component.
     * @return the initialized component instance
     */
    public ChoiceGroup getChoiceGroupMeio() {
        if (choiceGroupMeio == null) {//GEN-END:|52-getter|0|52-preInit
            // write pre-init user code here
            choiceGroupMeio = new ChoiceGroup("Voc\u00EA est\u00E1 usando:", Choice.MULTIPLE);//GEN-BEGIN:|52-getter|1|52-postInit
            choiceGroupMeio.append("CPTM", null);
            choiceGroupMeio.append("Metr\u00F4", null);
            choiceGroupMeio.setSelectedFlags(new boolean[] { false, false });//GEN-END:|52-getter|1|52-postInit
            // write post-init user code here
        }//GEN-BEGIN:|52-getter|2|
        return choiceGroupMeio;
    }
    //</editor-fold>//GEN-END:|52-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: backCommand ">//GEN-BEGIN:|57-getter|0|57-preInit
    /**
     * Returns an initiliazed instance of backCommand component.
     * @return the initialized component instance
     */
    public Command getBackCommand() {
        if (backCommand == null) {//GEN-END:|57-getter|0|57-preInit
            // write pre-init user code here
            backCommand = new Command("Voltar", Command.BACK, 5);//GEN-LINE:|57-getter|1|57-postInit
            // write post-init user code here
        }//GEN-BEGIN:|57-getter|2|
        return backCommand;
    }
    //</editor-fold>//GEN-END:|57-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formLinha ">//GEN-BEGIN:|65-getter|0|65-preInit
    /**
     * Returns an initiliazed instance of formLinha component.
     * @return the initialized component instance
     */
    public Form getFormLinha() {
        if (formLinha == null) {//GEN-END:|65-getter|0|65-preInit
            // write pre-init user code here
            formLinha = new Form("SMS-Den\u00FAncia", new Item[] { getChoiceGroupLinha() });//GEN-BEGIN:|65-getter|1|65-postInit
            formLinha.addCommand(getBackCommand());
            formLinha.setCommandListener(this);//GEN-END:|65-getter|1|65-postInit
            // write post-init user code here
        }//GEN-BEGIN:|65-getter|2|
        return formLinha;
    }
    //</editor-fold>//GEN-END:|65-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: choiceGroupLinha ">//GEN-BEGIN:|66-getter|0|66-preInit
    /**
     * Returns an initiliazed instance of choiceGroupLinha component.
     * @return the initialized component instance
     */
    public ChoiceGroup getChoiceGroupLinha() {
        if (choiceGroupLinha == null) {//GEN-END:|66-getter|0|66-preInit
            // write pre-init user code here
            choiceGroupLinha = new ChoiceGroup("Informe a Linha:", Choice.MULTIPLE);//GEN-LINE:|66-getter|1|66-postInit
            // write post-init user code here
        }//GEN-BEGIN:|66-getter|2|
        return choiceGroupLinha;
    }
    //</editor-fold>//GEN-END:|66-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formEstacao ">//GEN-BEGIN:|70-getter|0|70-preInit
    /**
     * Returns an initiliazed instance of formEstacao component.
     * @return the initialized component instance
     */
    public Form getFormEstacao() {
        if (formEstacao == null) {//GEN-END:|70-getter|0|70-preInit
            // write pre-init user code here
            formEstacao = new Form("SMS-Den\u00FAncia", new Item[] { getChoiceGroupEstacao() });//GEN-BEGIN:|70-getter|1|70-postInit
            formEstacao.addCommand(getBackCommand());
            formEstacao.setCommandListener(this);//GEN-END:|70-getter|1|70-postInit
            // write post-init user code here
        }//GEN-BEGIN:|70-getter|2|
        return formEstacao;
    }
    //</editor-fold>//GEN-END:|70-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: choiceGroupEstacao ">//GEN-BEGIN:|71-getter|0|71-preInit
    /**
     * Returns an initiliazed instance of choiceGroupEstacao component.
     * @return the initialized component instance
     */
    public ChoiceGroup getChoiceGroupEstacao() {
        if (choiceGroupEstacao == null) {//GEN-END:|71-getter|0|71-preInit
            // write pre-init user code here
            choiceGroupEstacao = new ChoiceGroup("", Choice.MULTIPLE);//GEN-LINE:|71-getter|1|71-postInit
            // write post-init user code here
        }//GEN-BEGIN:|71-getter|2|
        return choiceGroupEstacao;
    }
    //</editor-fold>//GEN-END:|71-getter|2|



    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formDentro ">//GEN-BEGIN:|72-getter|0|72-preInit
    /**
     * Returns an initiliazed instance of formDentro component.
     * @return the initialized component instance
     */
    public Form getFormDentro() {
        if (formDentro == null) {//GEN-END:|72-getter|0|72-preInit
            // write pre-init user code here
            formDentro = new Form("SMS-Den\u00FAncia", new Item[] { getChoiceGroupDentro() });//GEN-BEGIN:|72-getter|1|72-postInit
            formDentro.addCommand(getBackCommand());
            formDentro.setCommandListener(this);//GEN-END:|72-getter|1|72-postInit
            // write post-init user code here
        }//GEN-BEGIN:|72-getter|2|
        return formDentro;
    }
    //</editor-fold>//GEN-END:|72-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: choiceGroupDentro ">//GEN-BEGIN:|75-getter|0|75-preInit
    /**
     * Returns an initiliazed instance of choiceGroupDentro component.
     * @return the initialized component instance
     */
    public ChoiceGroup getChoiceGroupDentro() {
        if (choiceGroupDentro == null) {//GEN-END:|75-getter|0|75-preInit
            // write pre-init user code here
            choiceGroupDentro = new ChoiceGroup("O fato ocorreu:", Choice.MULTIPLE);//GEN-BEGIN:|75-getter|1|75-postInit
            choiceGroupDentro.append("Dentro do Trem", null);
            choiceGroupDentro.append("Na Esta\u00E7\u00E3o", null);
            choiceGroupDentro.setSelectedFlags(new boolean[] { false, false });//GEN-END:|75-getter|1|75-postInit
            // write post-init user code here
        }//GEN-BEGIN:|75-getter|2|
        return choiceGroupDentro;
    }
    //</editor-fold>//GEN-END:|75-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formSentido ">//GEN-BEGIN:|73-getter|0|73-preInit
    /**
     * Returns an initiliazed instance of formSentido component.
     * @return the initialized component instance
     */
    public Form getFormSentido() {
        if (formSentido == null) {//GEN-END:|73-getter|0|73-preInit
            // write pre-init user code here
            formSentido = new Form("SMS-Den\u00FAncia", new Item[] { getChoiceGroupSentido() });//GEN-BEGIN:|73-getter|1|73-postInit
            formSentido.addCommand(getBackCommand());
            formSentido.setCommandListener(this);//GEN-END:|73-getter|1|73-postInit
            // write post-init user code here
        }//GEN-BEGIN:|73-getter|2|
        return formSentido;
    }
    //</editor-fold>//GEN-END:|73-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: choiceGroupSentido ">//GEN-BEGIN:|77-getter|0|77-preInit
    /**
     * Returns an initiliazed instance of choiceGroupSentido component.
     * @return the initialized component instance
     */
    public ChoiceGroup getChoiceGroupSentido() {
        if (choiceGroupSentido == null) {//GEN-END:|77-getter|0|77-preInit
            // write pre-init user code here
            choiceGroupSentido = new ChoiceGroup("Sentido:", Choice.MULTIPLE);//GEN-LINE:|77-getter|1|77-postInit
            // write post-init user code here
        }//GEN-BEGIN:|77-getter|2|
        return choiceGroupSentido;
    }
    //</editor-fold>//GEN-END:|77-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: formCarro ">//GEN-BEGIN:|74-getter|0|74-preInit
    /**
     * Returns an initiliazed instance of formCarro component.
     * @return the initialized component instance
     */
    public Form getFormCarro() {
        if (formCarro == null) {//GEN-END:|74-getter|0|74-preInit
            // write pre-init user code here
            formCarro = new Form("SMS-Den\u00FAncia", new Item[] { getTextFieldCarro(), getStringItem() });//GEN-BEGIN:|74-getter|1|74-postInit
            formCarro.addCommand(getOkCommand());
            formCarro.addCommand(getBackCommand());
            formCarro.setCommandListener(this);//GEN-END:|74-getter|1|74-postInit
            // write post-init user code here
        }//GEN-BEGIN:|74-getter|2|
        return formCarro;
    }
    //</editor-fold>//GEN-END:|74-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: textFieldCarro ">//GEN-BEGIN:|98-getter|0|98-preInit
    /**
     * Returns an initiliazed instance of textFieldCarro component.
     * @return the initialized component instance
     */
    public TextField getTextFieldCarro() {
        if (textFieldCarro == null) {//GEN-END:|98-getter|0|98-preInit
            // write pre-init user code here
            textFieldCarro = new TextField("N\u00FAmero do Carro", null, 32, TextField.ANY);//GEN-LINE:|98-getter|1|98-postInit
            // write post-init user code here
        }//GEN-BEGIN:|98-getter|2|
        return textFieldCarro;
    }
    //</editor-fold>//GEN-END:|98-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: okCommand ">//GEN-BEGIN:|99-getter|0|99-preInit
    /**
     * Returns an initiliazed instance of okCommand component.
     * @return the initialized component instance
     */
    public Command getOkCommand() {
        if (okCommand == null) {//GEN-END:|99-getter|0|99-preInit
            // write pre-init user code here
            okCommand = new Command("Ok", "Ok", Command.OK, 1);//GEN-LINE:|99-getter|1|99-postInit
            // write post-init user code here
        }//GEN-BEGIN:|99-getter|2|
        return okCommand;
    }
    //</editor-fold>//GEN-END:|99-getter|2|
    //</editor-fold>





    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: textBoxSMS ">//GEN-BEGIN:|107-getter|0|107-preInit
    /**
     * Returns an initiliazed instance of textBoxSMS component.
     * @return the initialized component instance
     */
    public TextBox getTextBoxSMS() {
        if (textBoxSMS == null) {//GEN-END:|107-getter|0|107-preInit
            // write pre-init user code here
            textBoxSMS = new TextBox("SMS-Den\u00FAncia", "", 260, TextField.ANY);//GEN-BEGIN:|107-getter|1|107-postInit
            textBoxSMS.addCommand(getEnviarCommand());
            textBoxSMS.addCommand(getBackCommand());
            textBoxSMS.setCommandListener(this);//GEN-END:|107-getter|1|107-postInit
            // write post-init user code here
        }//GEN-BEGIN:|107-getter|2|
        return textBoxSMS;
    }
    //</editor-fold>//GEN-END:|107-getter|2|







    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: stringItem ">//GEN-BEGIN:|112-getter|0|112-preInit
    /**
     * Returns an initiliazed instance of stringItem component.
     * @return the initialized component instance
     */
    public StringItem getStringItem() {
        if (stringItem == null) {//GEN-END:|112-getter|0|112-preInit
            // write pre-init user code here
            stringItem = new StringItem("Na pr\u00F3xima tela, descreva o infrator para ajudar os seguran\u00E7as a identific\u00E1-lo.", null);//GEN-LINE:|112-getter|1|112-postInit
            // write post-init user code here
        }//GEN-BEGIN:|112-getter|2|
        return stringItem;
    }
    //</editor-fold>//GEN-END:|112-getter|2|

    //<editor-fold defaultstate="collapsed" desc=" Generated Getter: enviarCommand ">//GEN-BEGIN:|114-getter|0|114-preInit
    /**
     * Returns an initiliazed instance of enviarCommand component.
     * @return the initialized component instance
     */
    public Command getEnviarCommand() {
        if (enviarCommand == null) {//GEN-END:|114-getter|0|114-preInit
            // write pre-init user code here
            enviarCommand = new Command("Enviar", "Enviar", Command.OK, 1);//GEN-LINE:|114-getter|1|114-postInit
            // write post-init user code here
        }//GEN-BEGIN:|114-getter|2|
        return enviarCommand;
    }
    //</editor-fold>//GEN-END:|114-getter|2|





    /**
     * Returns a display instance.
     * @return the display instance.
     */
    public Display getDisplay () {
        return Display.getDisplay(this);
    }

    /**
     * Exits MIDlet.
     */
    public void exitMIDlet() {
        switchDisplayable (null, null);
        destroyApp(true);
        notifyDestroyed();
    }

    /**
     * Called when MIDlet is started.
     * Checks whether the MIDlet have been already started and initialize/starts or resumes the MIDlet.
     */
    public void startApp() {
        if (midletPaused) {
            resumeMIDlet ();
        } else {
            initialize ();
            startMIDlet ();
        }
        midletPaused = false;
    }

    /**
     * Called when MIDlet is paused.
     */
    public void pauseApp() {
        midletPaused = true;
    }

    /**
     * Called to signal the MIDlet to terminate.
     * @param unconditional if true, then the MIDlet has to be unconditionally terminated and all resources has to be released.
     */
    public void destroyApp(boolean unconditional) {
    }

    /**
     * Checa de tempos em tempo se o form atual foi preenchido (e avança).
     * <p>
     * O Midlet é usado como runnable numa thread iniciada por ele mesmo para isso.
     */
    public void run() {
        while (true) {
            doAutoForward();
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Avança para o próximo form *se* o atual já estiver preenchido
     */
    public void doAutoForward() {
        Displayable curr = getDisplay().getCurrent();
        for (int i = 0; i < FORMS.length; i++) {
            if (FORMS[i].equals(curr) && isSelected(CAMPOS[i])) {
                // Se a pessoa está fora do trem, pula o form de sentido
                if (getSelectedIndex(getChoiceGroupDentro()) == DENTRO_ESTACAO
                        && FORMS[i+1].equals(getFormSentido())) {
                    i++;
                }
                setCurrent(FORMS[i+1]);
                return;
            }
        }
    }

    /**
     * Volta para o form anterior (apagando a resposta que está nele)
     */
    public void doBack() {
        Displayable curr = getDisplay().getCurrent();
        for (int i = 0; i < FORMS.length; i++) {
            if (FORMS[i].equals(curr)) {
                clearSelection(CAMPOS[i-1]);
                setCurrent(FORMS[i-1]);
                // Se a pessoa está fora do trem, pula o form de sentido
                if (getSelectedIndex(getChoiceGroupDentro()) == DENTRO_ESTACAO
                        && FORMS[i-1].equals(getFormSentido())) {
                    clearSelection(CAMPOS[i-2]);
                    setCurrent(FORMS[i-2]);
                }
            }
        }
    }

    public void doMontaSMS() {
        // TODO: traduzir direito o tipodenuncia
        String denuncia = getSelectedString(getChoiceGroupTipo())
                + " na linha " + getSelectedString(getChoiceGroupLinha()) + ". ";
        if(getSelectedIndex(getChoiceGroupDentro()) == DENTRO_TREM) {
            denuncia += "Trem sentido " + getSelectedString(getChoiceGroupSentido());
            denuncia += " próx. da estação ";
        } else {
            denuncia += "Estação ";
        }
        denuncia += getSelectedString(getChoiceGroupEstacao()) + ".";
        if(getTextFieldCarro().getString().length()>0) {
            denuncia += " Carro " + getTextFieldCarro().getString() + ".";
        }
        getTextBoxSMS().setString(denuncia);
        setCurrent(getTextBoxSMS());
    }

    public void doSend() {
        class Sender implements Runnable {
            public void run() {
                try {
                    //        String denuncia = tipoDenuncia + " na linha " + linha.getText() + ". ";
                    //        if(dentro.isChecked()) {
                    //            denuncia += "Trem sentido " + sentido.getText();
                    //            denuncia += " próx. da estação ";
                    //        } else {
                    //            denuncia += "Estação ";
                    //        }
                    //        denuncia += estacao.getText() + ".";
                    //        if(getTextFieldCarro().getString().length()>0) {
                    //            denuncia += " Carro " + getTextFieldCarro().getString() + ".";
                    //        }
                    String addr = "sms://" + "12345677";
                    MessageConnection conn = (MessageConnection) Connector.open(addr);
                    TextMessage msg = (TextMessage) conn.newMessage(MessageConnection.TEXT_MESSAGE);
                    msg.setPayloadText("esse tem acentuação");
                    conn.send(msg);
                } catch (IOException ex) {
                    //getTextFieldSMS().setString(ex.getMessage());
                    ex.printStackTrace();
                }
            }
        }
        Thread t = new Thread(new Sender());
        t.start();




    }


    /**
     * Recupera o índice do elemento selecionado em um <code>ChoiceGroup</code>.
     * <p>
     * É usado no lugar do método homônimo do ChoiceGroup porque este não opera
     * em checkboxes (MULTIPLE), os quais usamos para permitir que a seleção venha
     * zerada no início.
     * @param cg
     * @return
     */
    private int getSelectedIndex(ChoiceGroup cg) {
        for (int i = 0; i < cg.size(); i++) {
            if (cg.isSelected(i)) {
                return i;
            }
        }
        return -1;
    }

    private String getSelectedString(ChoiceGroup cg) {
        return cg.getString(getSelectedIndex(cg));
    }

    /**
     * @param cg
     * @return <code>true</code> se o choicegroup for não-nulo e tiver pelo
     * menos um elemento preenchido.
     */
    private boolean isSelected(ChoiceGroup cg) {
        return (cg != null) && (getSelectedIndex(cg) >= 0);
    }

    /**
     * Inicializa e exibe um form (mas apenas se ele já não estiver sendo
     * exibido, caso contrário não faz nada).
     * @param d
     */
    private void setCurrent(Displayable d) {
        if (!d.equals(getDisplay().getCurrent())) {
            if (d.equals(getFormLinha())) {
                getChoiceGroupLinha().deleteAll();
                String[] linhas = LINHAS[getSelectedIndex(getChoiceGroupMeio())];
                for (int i = 0; i < linhas.length; i++) {
                    getChoiceGroupLinha().append(linhas[i], null);
                }
            }
            if (d.equals(getFormEstacao())) {
                getChoiceGroupEstacao().setLabel(getSelectedIndex(getChoiceGroupDentro()) == DENTRO_ESTACAO ?
                    "Estação onde você está:" :
                    "Próxima estação:");
                int numLinha = getNumLinhaEscolhida();
                getChoiceGroupEstacao().deleteAll();
                String[] estacoes = ESTACOES[numLinha];
                for (int i = 0; i < estacoes.length; i++) {
                    getChoiceGroupEstacao().append(estacoes[i], null);
                }
            }
            if (d.equals(getFormSentido())) {
                int numLinha = getNumLinhaEscolhida();
                String[] estacoes = ESTACOES[numLinha];
                getChoiceGroupSentido().deleteAll();
                getChoiceGroupSentido().append(estacoes[0], null);
                getChoiceGroupSentido().append(estacoes[estacoes.length - 1], null);
            }
            getDisplay().setCurrent(d);
        }
    }

    private void clearSelection(ChoiceGroup cg) {
        if (cg == null)
            return;
        for (int j= 0; j < cg.size(); j++) {
            cg.setSelectedIndex(j, false);
        }
    }

    private int getNumLinhaEscolhida() {
        int numLinha = getSelectedIndex(getChoiceGroupLinha()) + 1;
        if (getSelectedIndex(getChoiceGroupMeio()) == MEIO_CPTM) {
            numLinha += 6;
        }
        return numLinha;
    }


}
