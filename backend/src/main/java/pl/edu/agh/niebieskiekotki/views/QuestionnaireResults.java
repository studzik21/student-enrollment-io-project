package pl.edu.agh.niebieskiekotki.views;

import pl.edu.agh.niebieskiekotki.DataBaseMock;
import pl.edu.agh.niebieskiekotki.entitites.*;

import java.util.ArrayList;
import java.util.List;

public class QuestionnaireResults {

    List<String> headers;
    List<QuestionnaireResultsRow> rows;
    List<Term> questionnaireAvailableTerms;

    public QuestionnaireResults(List<Vote> votes, Questionnaire questionnaire){

         questionnaireAvailableTerms = new ArrayList<>();
         rows = new ArrayList<>();

        for(QuestionnaireTerm term : DataBaseMock.questionnaireTerms)
            if(term.getQuestionnaire().getId().equals(questionnaire.getId()))
                questionnaireAvailableTerms.add(term.getTerm());

        headers = new ArrayList<>();
        for(Term term : questionnaireAvailableTerms){
            headers.add(term.toString());
        }


        for(Vote vote : DataBaseMock.votes){
            QuestionnaireResultsRow  questionnaireResultsRow = null;

            if(! vote.getQuestionnaire().getId().equals(questionnaire.getId())) continue;

            for(QuestionnaireResultsRow row : rows)
                if(row.student == vote.getStudent())
                    questionnaireResultsRow = row;

            if(questionnaireResultsRow == null) {
                questionnaireResultsRow = new QuestionnaireResultsRow(vote.getStudent());
                rows.add(questionnaireResultsRow);
            }
            questionnaireResultsRow.setTerm(vote.getTerm());

        }
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public List<QuestionnaireResultsRow> getRows() {
        return rows;
    }

    public void setRows(List<QuestionnaireResultsRow> rows) {
        this.rows = rows;
    }

    public class QuestionnaireResultsRow{
        private Student student;
        private int[] studentChoose;

        public QuestionnaireResultsRow(Student student) {
            this.student = student;
            studentChoose = new int[questionnaireAvailableTerms.size()];
        }

        void setTerm(Term term){
            System.out.println(questionnaireAvailableTerms);
            System.out.println(term);
            Integer index = questionnaireAvailableTerms.indexOf(term);
            System.out.println(index);
            if(index == -1) return;
            studentChoose[index] = 1;
        }

        public int getStudentIndex() {
            return student.getIndexNumber();
        }

        public Student getStudent() {
            return student;
        }

        public void setStudent(Student student) {
            this.student = student;
        }

        public int[] getStudentChoose() {
            return studentChoose;
        }

        public void setStudentChoose(int[] studentChoose) {
            this.studentChoose = studentChoose;
        }

        public List<Term> getTermList(){
            ArrayList<Term> result = new ArrayList<>();
            for (int i = 0; i < studentChoose.length; i++) {
                if (studentChoose[i] == 1){
                    result.add(questionnaireAvailableTerms.get(i));
                }
            }
            return result;
        }
    }
}
