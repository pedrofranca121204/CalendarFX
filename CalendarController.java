package calendarfx;

import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import java.util.ArrayList;
import java.util.List;

public class CalendarController implements Initializable {

    @FXML private ComboBox<String> cmbMes;  
    @FXML private Spinner<Integer> spinYear;
    
    // Labels para os dias
    @FXML private Label dia1, dia2, dia3, dia4, dia5, dia6, dia7, dia8, dia9, dia10,
            dia11, dia12, dia13, dia14, dia15, dia16, dia17, dia18, dia19, dia20, dia21, 
            dia22, dia23, dia24, dia25, dia26, dia27, dia28, dia29, dia30, dia31;

    private List<Label> dias;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicializa lista de Labels
        dias = new ArrayList<>();
        dias.add(dia1); dias.add(dia2); dias.add(dia3); dias.add(dia4); dias.add(dia5);
        dias.add(dia6); dias.add(dia7); dias.add(dia8); dias.add(dia9); dias.add(dia10);
        dias.add(dia11); dias.add(dia12); dias.add(dia13); dias.add(dia14); dias.add(dia15);
        dias.add(dia16); dias.add(dia17); dias.add(dia18); dias.add(dia19); dias.add(dia20);
        dias.add(dia21); dias.add(dia22); dias.add(dia23); dias.add(dia24); dias.add(dia25);
        dias.add(dia26); dias.add(dia27); dias.add(dia28); dias.add(dia29); dias.add(dia30);
        dias.add(dia31);

        // Meses
        cmbMes.getItems().addAll("Janeiro", "Fevereiro", "Março", "Abril",
                                 "Maio", "Junho", "Julho", "Agosto",
                                 "Setembro", "Outubro", "Novembro", "Dezembro");

        // Data atual
        LocalDate hoje = LocalDate.now();
        int mesAtual = hoje.getMonthValue() - 1; // ComboBox usa índice 0-11
        int anoAtual = hoje.getYear();

        cmbMes.getSelectionModel().select(mesAtual);

        spinYear.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1900, 2099, anoAtual));

        // Atualiza o calendário ao iniciar
        escreverMes(anoAtual, mesAtual);

        // Listeners para atualizar quando o usuário mudar o mês ou ano
        cmbMes.setOnAction(e -> atualizarCalendario());
        spinYear.valueProperty().addListener((obs, oldVal, newVal) -> atualizarCalendario());
    }

    private void atualizarCalendario() {
        int ano = spinYear.getValue();
        int mes = cmbMes.getSelectionModel().getSelectedIndex(); // 0-11
        escreverMes(ano, mes);
    }

    private void escreverMes(int ano, int mes) {
        // Limpa os Labels
        for (Label label : dias) {
            label.setText("");
        }

        // Descobre o primeiro dia do mês e quantos dias o mês tem
        YearMonth ym = YearMonth.of(ano, mes + 1); // LocalDate usa 1-12
        int diasNoMes = ym.lengthOfMonth();
        LocalDate primeiroDia = ym.atDay(1);
        DayOfWeek diaSemana = primeiroDia.getDayOfWeek(); // MONDAY = 1 ... SUNDAY = 7

        int indiceInicial = diaSemana.getValue() % 7; // Ajusta para DOMINGO = 0

        for (int i = 0; i < diasNoMes; i++) {
            int posicao = indiceInicial + i;
            if (posicao < dias.size()) {
                dias.get(posicao).setText(String.valueOf(i + 1));
            }
        }
    }
}
