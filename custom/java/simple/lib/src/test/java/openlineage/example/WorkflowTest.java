package openlineage.example;

import openlineage.example.workflows.SimpleWorkflow;
import org.junit.Test;

public class WorkflowTest {
  @Test
  public void testWorkflow() {
    // Test used as driver
    final SimpleWorkflow workflow = new SimpleWorkflow();
    workflow.run();
  }
}
