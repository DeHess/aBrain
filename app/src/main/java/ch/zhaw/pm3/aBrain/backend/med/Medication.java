package ch.zhaw.pm3.aBrain.backend.med;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

/**
 * The type Medication.
 */
public class Medication {

    private int medId;
    private final StringProperty name = new SimpleStringProperty("");
    private int stock;
    private TreatmentPlan treatmentPlan;
    
    private ObjectProperty<Image> image = new SimpleObjectProperty<>(null);

    private Date expirationDate;

    /**
     * Instantiates a new Medication.
     *
     * @param name the name
     */
    public Medication(String name){
        setName(name);
    }
    
    /**
     * Instantiates a new Medication.
     *
     * @param name           the name
     * @param stock          the stock
     * @param treatmentStart the treatment start
     */
    public Medication(String name, int stock, LocalDateTime treatmentStart){
        this(name);
        this.stock = stock;
        this.treatmentPlan = new TreatmentPlan(treatmentStart);
    }
    
    /**
     * Instantiates a new Medication.
     *
     * @param medId          the medid
     * @param name           the name
     * @param stock          the stock
     * @param treatmentStart the treatment start
     * @param image          the image
     */
    public Medication(int medId, String name, int stock, LocalDateTime treatmentStart, Image image){
        this(name, stock, treatmentStart);
        setImage(image);
        setMedId(medId);
    }

    /**
     * Instantiates a new Medication.
     *
     * @param medId          the medid
     * @param name           the name
     * @param stock          the stock
     * @param treatmentStart the treatment start
     * @param treatmentEnd   the treatment end
     */
    public Medication(int medId, String name, int stock, LocalDateTime treatmentStart, LocalDateTime treatmentEnd){
        this(name, stock, treatmentStart);
        this.treatmentPlan = new TreatmentPlan(treatmentStart, treatmentEnd);
        setMedId(medId);
    }

    /**
     * Instantiates a new Medication.
     *
     * @param medId          the medid
     * @param name           the name
     * @param stock          the stock
     * @param treatmentStart the treatment start
     * @param treatmentEnd   the treatment end
     * @param image          the image
     */
    public Medication(int medId, String name, int stock, LocalDateTime treatmentStart, LocalDateTime treatmentEnd, Image image){
        this(medId, name, stock, treatmentStart, treatmentEnd);
        setImage(image);
    }

    public void setName(String name){
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty getNameProperty(){return name;}

    public ObjectProperty<Image> getImageProperty(){
        return image;
    }

    public void setImage(Image image){
        this.image.set(image);
    }

    public Optional<Image> getImage() {
        return Optional.ofNullable(image.get());
    }

    public void setStock(int stock){
        this.stock = stock;
    }
    public int getStock() {
        return stock;
    }

    public void setMedId(int medId) {
        this.medId = medId;
    }
    public int getMedId() {
        return medId;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public LocalDateTime getTreatmentStart() {
        return treatmentPlan.getTreatmentStart();
    }

    public Optional<LocalDateTime> getTreatmentEnd() {
        return Optional.ofNullable(treatmentPlan.getTreatmentEnd());
    }

    public void setTreatmentPlan(TreatmentPlan treatmentPlan) {
        this.treatmentPlan = treatmentPlan;
    }

    public void removeTreatmentPlan(){
        treatmentPlan = null;
    }

    public TreatmentPlan getTreatmentPlan(){
        return treatmentPlan;
    }
}
