package com.danielglover.circuitapp.logic;

import com.danielglover.circuitapp.logic.circuit.Circuit;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class CircuitVariable {
        private final String name;
        private final BooleanProperty value = new SimpleBooleanProperty(false);

        public CircuitVariable(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public boolean getValue() {
            return value.get();
        }

        public void setValue(boolean v) {
            value.set(v);

        }

        public BooleanProperty valueProperty() {
            return value;
        }
}
