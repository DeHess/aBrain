package ch.zhaw.pm3.aBrain.frontend.factory;

import ch.zhaw.pm3.aBrain.backend.med.Medication;
import ch.zhaw.pm3.aBrain.frontend.controller.PharmacyScreenController;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;
import java.util.Objects;

/**
 * Serves as a factory for the grid in the class {@link PharmacyScreenController} It
 * provides cells containing the medication name and image.
 *
 * @author baermlau
 */
public class PharmacyItem {
    
    private static final String EMPTY_IMAGE_PATH = "src/main/resources/ch/zhaw/pm3/aBrain/design/img_elements/image_emptyMed.png";
    private static final String DEFAULT_IMAGE_PATH = "src/main/resources/ch/zhaw/pm3/aBrain/design/img_elements/image_defaultMed.png";
    
    private static final String STYLE_LABEL = "pharmacy__label";
    private static final String STYLE_NODE = "pharmacy__node";
    private static final String STYLE_IMAGE = "pharmacy__image";
    private final StringProperty medicationName;
    private final ObjectProperty<Image> medicationImage;
    private VBox cell;
    
    private final Medication medication;
    
    /**
     * Instantiates a new Pharmacy item.
     *
     * @throws FileNotFoundException the file not found exception
     */
    public PharmacyItem() throws FileNotFoundException {
        medication = null;
        medicationName = new SimpleStringProperty("");
        Image empty = ImageLoader.getImageFromPath(EMPTY_IMAGE_PATH);
        medicationImage = new SimpleObjectProperty<>(empty);
        createNode();
    }
    
    /**
     * Instantiates a new Pharmacy item.
     *
     * @param medication the medication
     * @throws FileNotFoundException the file not found exception
     */
    public PharmacyItem(Medication medication) throws FileNotFoundException {
        this.medication = Objects.requireNonNull(medication);
        medicationName = medication.getNameProperty();
        medicationImage = medication.getImageProperty();
        if (medicationImage.get() == null) medicationImage.set(ImageLoader.getImageFromPath(DEFAULT_IMAGE_PATH));
        createNode();
    }
    
    /**
     * Get medication.
     *
     * @return the medication
     */
    public Medication getMedication() {
        return medication;
    }
    
    private void createNode() {
        cell = new VBox();
        cell.getStyleClass().add(STYLE_NODE);
        cell.idProperty().bind(medicationName);
        ImageView imageView = createImageView();
        Label label = createLabel();
        label.setLabelFor(imageView);
        imageView.fitHeightProperty().bind(cell.heightProperty().subtract(label.heightProperty()));
        
        cell.getChildren().addAll(imageView, label);
    }
    
    private ImageView createImageView() {
        ImageView imageView = new ImageView(medicationImage.get());
        imageView.getStyleClass().add(STYLE_IMAGE);
        imageView.setPreserveRatio(true);
        imageView.idProperty().bind(medicationName);
        imageView.imageProperty().bind(medicationImage);
        return imageView;
    }
    
    private Label createLabel() {
        Label label = new Label();
        label.getStyleClass().add(STYLE_LABEL);
        label.textProperty().bind(medicationName);
        label.idProperty().bind(medicationName);
        label.setTooltip(getTooltipForLabel());
        return label;
    }
    
    private Tooltip getTooltipForLabel() {
        // TODO: Make compatible for different languages (i.e. get sentence from different class)
        if (isEmpty()) return new Tooltip("Füge ein neues Medikament hinzu");
        else return new Tooltip("Bearbeite das Medikament");
    }
    
    /**
     * Is the cell linked to a medication.
     *
     * @return true if the object medication is null else return false {@link Medication medication}
     */
    public Boolean isEmpty() {
        return medication == null;
    }
    
    /**
     * Gets the cell object.
     *
     * @return the cell
     */
    public VBox getCell() {
        return cell;
    }
    
}
