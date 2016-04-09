package hu.progtech.cd2t100.computation;

import java.util.Optional;

public final class InstructionBuilder {
	private InstructionInfo instructionInfo;

	private String opcode;

	private ArgumentChecker argumentChecker;

	private InstructionRegistry instructionRegistry;

	public InstructionBuilder(InstructionRegistry instructionRegistry,
	                          ArgumentChecker argumentChecker) {
		instructionInfo = null;

		this.instructionRegistry = instructionRegistry;

		this.argumentChecker = argumentChecker;
	}

	public InstructionBuilder addArg(Argument argument)
		throws Exception, InvalidArgumentTypeException {
		if (instructionInfo == null) {
			throw new Exception("Instruction is unset, cannot determine aguments!");
		}

		return this;
	}

	public InstructionBuilder addOpcode(String opcode)
		throws Exception {
		Optional<InstructionInfo> instructionInfoOpt =
			instructionRegistry.getInstructionInfoFor(opcode);

		if (!instructionInfoOpt.isPresent()) {
			throw new Exception("Unknown opcode: " + opcode);
		}

		instructionInfo = instructionInfoOpt.get();

		return this;
	}

	/*public Instruction build() {
		return null;
	}*/
}
