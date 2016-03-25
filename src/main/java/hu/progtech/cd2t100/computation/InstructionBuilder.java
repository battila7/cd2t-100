package hu.progtech.cd2t100.computation;

/**
*/
public final class InstructionBuilder {
	private String opcode;
	private Argument[] args;

	public InstructionBuilder addArg(Argument argument) {
		return this;
	}

	public InstructionBuilder addOpcode(String opcode) {
		return this;
	}

	public Instruction build() {
		return null;
	}
}
