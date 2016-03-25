package hu.progtech.cd2t100.computation;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public final class InstructionBuilder {
	private InstructionInfo instructionInfo;

	private String opcode;

	private Argument[] args;

	private InstructionRegistry instructionRegistry;

	public InstructionBuilder(InstructionRegistry instructionRegistry) {
		instructionInfo = null;

		this.instructionRegistry = instructionRegistry;
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
			instructionInfo = instructionInfoOpt.get();
		} else {
			throw new Exception("Unknown opcode: " + opcode);
		}

		return this;
	}

	public Instruction build() {
		return null;
	}
}
