package ch.zhaw.pm3.aBrain.frontend;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * This class is responsible for the scrolling of the start screen.
 *
 * @author wilphi01
 * @version 1.0
 */
public class ZoomableScrollPane extends ScrollPane {
    private double scaleValue = 0.7;
    private double zoomIntensity = 0.04;
    private Node target;
    private Node zoomNode;

    /**
     * This constructor creates the ZoomableScrollPane with its basic settings.
     * @param target is the affected node.
     */
    public ZoomableScrollPane(Node target) {
        super();
        this.target = target;
        this.zoomNode = new Group(target);
        setContent(outerNode(zoomNode));

        setPannable(true);
        setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        setFitToHeight(true); //center
        setFitToWidth(true); //center

        updateScale();
    }

    /**
     * This method creates the outer node.
     * @param node is the affected node.
     * @return the outer node.
     */
    private Node outerNode(Node node) {
        Node outerNode = centeredNode(node);
        outerNode.setOnScroll(e -> {
            e.consume();
            onScroll(e.getTextDeltaY(), new Point2D(e.getX(), e.getY()));
        });
        return outerNode;
    }

    /**
     * This method centres the affected node.
     */
    private Node centeredNode(Node node) {
        VBox vBox = new VBox(node);
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }

    /**
     * This method updates the scaling of the node.
     */
    private void updateScale() {
        target.setScaleX(scaleValue);
        target.setScaleY(scaleValue);
    }

    /**
     * This method is responsible for scrolling.
     * @param wheelDelta is the scrolling speed.
     * @param mousePoint is the position of the mouse.
     */
    private void onScroll(double wheelDelta, Point2D mousePoint) {
        double zoomFactor = Math.exp(wheelDelta * zoomIntensity);

        Bounds innerBounds = zoomNode.getLayoutBounds();
        Bounds viewportBounds = getViewportBounds();

        // calculate pixel offsets from [0, 1] range
        double valX = this.getHvalue() * (innerBounds.getWidth() - viewportBounds.getWidth());
        double valY = this.getVvalue() * (innerBounds.getHeight() - viewportBounds.getHeight());

        scaleValue = scaleValue * zoomFactor;
        updateScale();
        this.layout(); // refresh ScrollPane scroll positions & target bounds

        // convert target coordinates to zoomTarget coordinates
        Point2D posInZoomTarget = target.parentToLocal(zoomNode.parentToLocal(mousePoint));

        // calculate adjustment of scroll position (pixels)
        Point2D adjustment = target.getLocalToParentTransform().deltaTransform(posInZoomTarget.multiply(zoomFactor - 1));

        // convert back to [0, 1] range
        // (too large/small values are automatically corrected by ScrollPane)
        Bounds updatedInnerBounds = zoomNode.getBoundsInLocal();
        this.setHvalue((valX + adjustment.getX()) / (updatedInnerBounds.getWidth() - viewportBounds.getWidth()));
        this.setVvalue((valY + adjustment.getY()) / (updatedInnerBounds.getHeight() - viewportBounds.getHeight()));
    }
}

