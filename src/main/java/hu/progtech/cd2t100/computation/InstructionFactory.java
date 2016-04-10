package hu.progtech.cd2t100.computation;

import java.util.Optional;

import hu.progtech.cd2t100.formal.InstructionInfo;

public final class InstructionFactory {
	private InstructionInfo instructionInfo;

	private String opcode;

	private ArgumentChecker argumentChecker;

	private InstructionRegistry instructionRegistry;

	public InstructionFactory(InstructionRegistry instructionRegistry) {
		instructionInfo = null;

		this.instructionRegistry = instructionRegistry;
	}

	/*public InstructionBuilder addArg(Argument argument)
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
	}*/
}
