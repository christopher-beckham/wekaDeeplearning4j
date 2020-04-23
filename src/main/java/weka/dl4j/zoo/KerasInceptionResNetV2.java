package weka.dl4j.zoo;

import org.deeplearning4j.nn.graph.ComputationGraph;
import weka.dl4j.PretrainedType;
import weka.dl4j.zoo.keras.InceptionResNetV2;

// TODO write custom lambda layers for block35, block17, block8: https://github.com/keras-team/keras-applications/blob/master/keras_applications/inception_resnet_v2.py#L168
public class KerasInceptionResNetV2 extends AbstractZooModel {

    private static final long serialVersionUID = -7137786081088311216L;

    private InceptionResNetV2.VARIATION variation = InceptionResNetV2.VARIATION.STANDARD;

    public KerasInceptionResNetV2() {
        setVariation(InceptionResNetV2.VARIATION.STANDARD);
        setPretrainedType(PretrainedType.IMAGENET);
    }

    public InceptionResNetV2.VARIATION getVariation() {
        return variation;
    }

    public void setVariation(InceptionResNetV2.VARIATION var) {
        variation = var;
        // We may need to update the pretrained values based on the new variation
        setPretrainedType(m_pretrainedType);
    }

    @Override
    public void setPretrainedType(PretrainedType pretrainedType) {
        int numFExtractOutputs = -1;
        switch (variation) {
            case STANDARD:
                numFExtractOutputs = 1536;
                break;
        }

        setPretrainedType(pretrainedType, numFExtractOutputs, "avg_pool", "predictions");
    }

    @Override
    public ComputationGraph init(int numLabels, long seed, int[] shape, boolean filterMode) {
        InceptionResNetV2 inceptionResNetV2 = new InceptionResNetV2();
        inceptionResNetV2.setVariation(variation);

        return attemptToLoadWeights(inceptionResNetV2, null, seed, numLabels, filterMode);
    }

    @Override
    public int[][] getShape() {
        int[][] shape = new int[1][];
        shape[0] = InceptionResNetV2.inputShape;
        return shape;
    }
}
